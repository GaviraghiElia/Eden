package com.unimib.eden.ui.insertProduct;

import static com.unimib.eden.utils.Constants.PRODUCT_OTHER_INFORMATION;
import static com.unimib.eden.utils.Constants.PRODUCT_CURRENT_PHASE;
import static com.unimib.eden.utils.Constants.PRODUCT_OFFERS;
import static com.unimib.eden.utils.Constants.PRODUCT_PLANT;
import static com.unimib.eden.utils.Constants.PRODUCT_PRICE;
import static com.unimib.eden.utils.Constants.PRODUCT_QUANTITY;
import static com.unimib.eden.utils.Constants.PRODUCT_EXCHANGE_AVAILABLE;
import static com.unimib.eden.utils.Constants.PRODUCT_TYPE;
import static com.unimib.eden.utils.Constants.PRODUCT_SELLER;

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

import com.google.firebase.auth.FirebaseAuth;
import com.unimib.eden.databinding.ActivityInserimentoProdottoBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import com.unimib.eden.R;
import com.unimib.eden.model.Phase;
import com.unimib.eden.model.Plant;
import com.unimib.eden.ui.searchPlant.SearchPlantActivity;
import com.unimib.eden.utils.Constants;

/**
 * Activity for inserting a new product.
 * This activity allows the user to enter details of a new product and add it to the database.
 */
public class InsertProductActivity extends AppCompatActivity {
    private static final String TAG = "InsertProductActivity";
    private InsertProductViewModel insertProductViewModel;
    private ActivityInserimentoProdottoBinding mBinding;
    private String plantId = "";
    private Plant plant;
    private String lastPhase = "";
    private int phasePosition;
    private FirebaseAuth firebaseAuth;
    ArrayList<String> phasesNames = new ArrayList<>();
    List<Phase> phasesList;
    ArrayAdapter<String> adapter;


    /**
     * Method called when the activity is created. Here, the user interface components are initialized,
     * listeners are set, and necessary data is obtained from the ViewModel.
     *
     * @param savedInstanceState A Bundle object containing the previous state of the activity,
     *                           if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        insertProductViewModel = new ViewModelProvider(this).get(InsertProductViewModel.class);

        firebaseAuth = FirebaseAuth.getInstance();
        mBinding = ActivityInserimentoProdottoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.toolbarInsProd.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        mBinding.toolbarInsProd.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBinding.prezzo.addTextChangedListener(productTextWatcher);
        mBinding.quantity.addTextChangedListener(productTextWatcher);
        mBinding.plant.addTextChangedListener(productTextWatcher);
        mBinding.autoCompleteTextViewPhases.addTextChangedListener(productTextWatcher);


        ActivityResultLauncher<Intent> searchPiantaActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == Activity.RESULT_OK) {
                            Intent date = o.getData();
                            plant = (Plant) date.getSerializableExtra("pianta");
                            plantId = plant.getId();
                            mBinding.plant.setText(plant.getName());
                            mBinding.toolbarInsProd.setTitle("Inserisci " + plant.getName());
                            try {
                                phasesList = insertProductViewModel.getPhasesList(plant.getPhases());
                                if (!phasesNames.isEmpty()) {
                                    phasesNames.clear();
                                }
                                for (Phase f : phasesList) {
                                    phasesNames.add(f.getPhaseName());
                                }

                            } catch (ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            lastPhase = phasesNames.get(phasesNames.size() - 1);
                            adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_menu_item, phasesNames);
                        }
                    }
                });
        mBinding.plant.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Intent intent = new Intent(getApplicationContext(), SearchPlantActivity.class);
                    intent.putExtra("operationCode", Constants.CREATE_PRODUCT_OPERATION_CODE);
                    searchPiantaActivityResultLauncher.launch(intent);
                    mBinding.plant.clearFocus();
                }
            }
        });


        adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_item, phasesNames);
        mBinding.autoCompleteTextViewPhases.setAdapter(adapter);

        mBinding.buttonSubmit.setOnClickListener(v -> {
            addProduct();
        });


        mBinding.autoCompleteTextViewPhases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                phasePosition = position;
                if (position == phasesNames.size() - 1) {
                    mBinding.quantitaTextInputLayout.setHint(getText(R.string.quantita_grammi));
                } else {
                    mBinding.quantitaTextInputLayout.setHint(getText(R.string.quantita_piante));
                }
            }
        });
    }
    private final TextWatcher productTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String price = mBinding.prezzo.getText().toString();
            String quantity = mBinding.quantity.getText().toString();
            String plantText = mBinding.plant.getText().toString();
            String phaseText = mBinding.autoCompleteTextViewPhases.getText().toString();
            mBinding.buttonSubmit.setEnabled(!price.isEmpty() && !quantity.isEmpty() && !plantText.isEmpty() && !phaseText.isEmpty());
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
     * Method called when the user clicks the "Submit" button to add a new product.
     * Gathers the data entered by the user from the user interface and sends it to the ViewModel
     * to add the product to the database.
     */
    private void addProduct() {
        double price = Double.parseDouble(mBinding.prezzo.getText().toString());
        int quantity = Integer.parseInt(mBinding.quantity.getText().toString());
        String otherInformation = mBinding.altreInformazioni.getText().toString();
        Boolean exchangeAvailable = mBinding.checkBoxDisponibileAScambi.isChecked();
        String currentPhase = mBinding.autoCompleteTextViewPhases.getText().toString();
        String phaseId = phasesList.get(phasePosition).getId();

        String type;
        // Check if the last phase is equal to the chosen one
        if(currentPhase.equals(lastPhase)){
            type="eccedenza";
        }
        else {
            type="coltura";
        }

        Map<String, Object> product = new HashMap<>();
        product.put(PRODUCT_TYPE, type);
        product.put(PRODUCT_PRICE, price);
        product.put(PRODUCT_PLANT, plantId);
        product.put(PRODUCT_QUANTITY, quantity);
        product.put(PRODUCT_CURRENT_PHASE, phaseId);
        product.put(PRODUCT_OTHER_INFORMATION, otherInformation);

        String user = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        product.put(PRODUCT_SELLER, user);
        product.put(PRODUCT_OFFERS, null);
        product.put(PRODUCT_EXCHANGE_AVAILABLE, exchangeAvailable);

        insertProductViewModel.addProduct(product);
        finish();
    }
}
