package com.unimib.eden.ui.cropDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.R;
import com.unimib.eden.databinding.ActivityCropDetailsBinding;
import com.unimib.eden.model.Crop;
import com.unimib.eden.ui.plantDetails.PlantDetailsActivity;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.Converters;

import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Activity for displaying the details of a crop.
 * This class handles the display and interaction with the crop details UI.
 */
public class CropDetailsActivity extends AppCompatActivity {

    private Crop crop;
    private CropDetailsViewModel cropDetailsViewModel;
    private ActivityCropDetailsBinding mBinding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MaterialDatePicker.Builder materialDateBuilder;
    private MaterialDatePicker materialDatePicker;

    private static final String TAG = "CropDetailsActivity";

    /**
     * Default constructor for CropDetailsActivity.
     */
    public CropDetailsActivity() {}

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityCropDetailsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // Set up the app bar
        mBinding.toolbarColturaDetails.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        mBinding.toolbarColturaDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cropDetailsViewModel = new ViewModelProvider(this).get(CropDetailsViewModel.class);
        Intent intent = getIntent();
        crop = (Crop) intent.getSerializableExtra("coltura");
        cropDetailsViewModel.initialize(crop);
        mBinding.toolbarColturaDetails.setTitle(crop.getPlantName());

        mBinding.textViewLastWateringFull.setText(cropDetailsViewModel.getNextWatering(this, crop));
        mBinding.textViewInsertionDateFull.setText(Converters.dateToString(crop.getInsertionDate()));
        try {
            mBinding.textViewCurrentPhaseFull.setText(cropDetailsViewModel.getPhaseName(crop));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (crop.getNote().isEmpty()) {
            mBinding.cardNotes.setVisibility(View.GONE);
        } else {
            mBinding.cardNotes.setVisibility(View.VISIBLE);
            mBinding.textViewNoteFull.setText(crop.getNote());
        }
        mBinding.textViewQuantityFull.setText(String.valueOf(crop.getQuantity()));

        mBinding.buttonViewAllDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlantDetailsActivity.class);
                intent.putExtra("operationCode", Constants.PLANT_DETAILS_OPERATION_CODE);
                intent.putExtra("pianta", cropDetailsViewModel.getPlant(crop));
                startActivity(intent);
            }
        });

        mBinding.buttonWaterCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDateBuilder = MaterialDatePicker.Builder.datePicker();
                materialDateBuilder.setTitleText(R.string.date_picker_title);
                materialDateBuilder.setCalendarConstraints(new CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now()).build());
                materialDatePicker = materialDateBuilder.build();
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                    cropDetailsViewModel.updateWateringDateCrop(crop, new Date(materialDatePicker.getHeaderText()));
                    crop.setLastWatering(new Date(materialDatePicker.getHeaderText()));
                    mBinding.textViewLastWateringFull.setText(cropDetailsViewModel.getNextWatering(getApplicationContext(), crop));
                    mBinding.textViewInsertionDateFull.setText(Converters.dateToString(crop.getInsertionDate()));
                });
            }
        });

        mBinding.buttonModifyPhase.setVisibility(View.INVISIBLE);
    }
}
