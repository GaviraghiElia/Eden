package com.unimib.eden.ui.insertCrop;

import static com.unimib.eden.utils.Constants.CROPS_INSERTION_DATE;
import static com.unimib.eden.utils.Constants.CROPS_CURRENT_PHASE;
import static com.unimib.eden.utils.Constants.CROPS_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.CROPS_CURRENT_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.CROPS_NOTES;
import static com.unimib.eden.utils.Constants.CROPS_PLANT;
import static com.unimib.eden.utils.Constants.CROPS_OWNER;
import static com.unimib.eden.utils.Constants.CROPS_QUANTITY;
import static com.unimib.eden.utils.Constants.CROPS_LAST_WATERING;
import static com.unimib.eden.utils.Constants.PLANT_NAME;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.unimib.eden.databinding.ActivityInserimentoColturaBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.unimib.eden.R;
import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.ui.searchPlant.SearchPlantActivity;
import com.unimib.eden.utils.Constants;

/**
 * Activity for inserting a new crop.
 * This activity allows the user to input the details of a new crop and add it to the database.
 */
public class InsertCropActivity extends AppCompatActivity {
    private static final String TAG = "InsertCropActivity";
    private InsertCropViewModel insertCropViewModel;
    private ActivityInserimentoColturaBinding mBinding;
    private String plantId = "";
    private String plantName = "";
    private Pianta plant;
    private int phase = -1;
    private FirebaseAuth firebaseAuth;

    ArrayList<String> phasesNames = new ArrayList<>();

    ArrayList<Fase> phasesList;
    ArrayList<Integer> frequencies;
    ArrayAdapter<String> adapter;


    /**
     * Called when the activity is first created. This is where you should do all of your
     * normal static set up: create views, bind data to lists, etc.
     *
     * @param savedInstanceState Bundle: If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        insertCropViewModel = new ViewModelProvider(this).get(InsertCropViewModel.class);

        firebaseAuth = FirebaseAuth.getInstance();
        mBinding = ActivityInserimentoColturaBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.toolbarInsColt.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        mBinding.toolbarInsColt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBinding.quantita.addTextChangedListener(cropTextWatcher);
        mBinding.pianta.addTextChangedListener(cropTextWatcher);
        mBinding.autoCompleteTextViewFasi.addTextChangedListener(cropTextWatcher);

        ActivityResultLauncher<Intent> searchPiantaActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == Activity.RESULT_OK) {
                            Intent data = o.getData();
                            plant = (Pianta) data.getSerializableExtra("pianta");
                            plantId = plant.getId();
                            plantName = plant.getNome();
                            mBinding.pianta.setText(plant.getNome());
                            mBinding.toolbarInsColt.setTitle("Inserisci " + plant.getNome().toLowerCase());
                            try {
                                phasesList = insertCropViewModel.getPhasesList(plant.getFasi());
                                frequencies = insertCropViewModel.getWateringFrequency(plant.getFasi());
                                if (!phasesNames.isEmpty()) {
                                    phasesNames.clear();
                                }
                                for (Fase f : phasesList) {
                                    phasesNames.add(f.getNomeFase());
                                }
                            } catch (ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_item, phasesNames);
                        }
                    }
                });
        mBinding.pianta.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Intent intent = new Intent(getApplicationContext(), SearchPlantActivity.class);
                    intent.putExtra("operationCode", Constants.CREATE_CROP_OPERATION_CODE);
                    searchPiantaActivityResultLauncher.launch(intent);
                    mBinding.pianta.clearFocus();
                }
            }
        });

        adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_item, phasesNames);
        mBinding.autoCompleteTextViewFasi.setAdapter(adapter);
        mBinding.buttonSubmit.setOnClickListener(v -> {
            addCrop();
        });

        mBinding.autoCompleteTextViewFasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                phase =position;
            }
        });
    }

    private final TextWatcher cropTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String quantity = mBinding.quantita.getText().toString();
            String plantText = mBinding.pianta.getText().toString();
            String phasesText = mBinding.autoCompleteTextViewFasi.getText().toString();
            mBinding.buttonSubmit.setEnabled(!quantity.isEmpty() && !plantText.isEmpty() && !phasesText.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Method called when the user presses the "Submit" button to add a new crop.
     * It collects the data entered by the user from the user interface and sends it to the ViewModel
     * for adding the crop to the database.
     */
    private void addCrop() {
        int quantity = Integer.parseInt(mBinding.quantita.getText().toString());
        String note = mBinding.note.getText().toString();

        Map<String, Object> crop = new HashMap<>();
        String user = firebaseAuth.getCurrentUser().getUid();
        crop.put(CROPS_OWNER, user);
        crop.put(CROPS_PLANT, plantId);
        crop.put(CROPS_QUANTITY, quantity);
        crop.put(CROPS_NOTES, note);

        Date now = new Date();
        Timestamp timestamp = new Timestamp(now);
        crop.put(CROPS_INSERTION_DATE, timestamp);
        crop.put(CROPS_LAST_WATERING, timestamp);

        crop.put(CROPS_CURRENT_PHASE, phase);
        crop.put(PLANT_NAME, plantName);
        crop.put(CROPS_WATERING_FREQUENCY, frequencies);
        crop.put(CROPS_CURRENT_WATERING_FREQUENCY, frequencies.get(phase));
        insertCropViewModel.addCrop(crop);
        finish();
    }
}