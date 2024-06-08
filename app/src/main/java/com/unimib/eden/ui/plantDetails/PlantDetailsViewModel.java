package com.unimib.eden.ui.plantDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.model.Fase;
import com.unimib.eden.repository.PhaseRepository;
import com.unimib.eden.repository.PlantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * PlantDetailsViewModel class representing the ViewModel of the PlantDetailsActivity.
 */
public class PlantDetailsViewModel extends AndroidViewModel {
    private static final String TAG = "PlantDetailsViewModel";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PlantRepository plantRepository;
    private PhaseRepository phaseRepository;

    private List<Fase> phasesList;

    /**
     * Constructor that generates an instance of the PlantDetailsViewModel with an instance of PiantaRepository, FaseRepository, and an empty list of phases.
     *
     * @param application The context of the application.
     */
    public PlantDetailsViewModel(@NonNull Application application) {
        super(application);
        plantRepository = new PlantRepository(application);
        phaseRepository = new PhaseRepository(application);

        phasesList = new ArrayList<>();
    }

    /**
     * Method getPhasesList that returns a list of phases with IDs matching the input list.
     *
     * @param phasesIds The list of IDs that the returned phases must match.
     * @return A list of phases with IDs matching the input list.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Fase> getPhasesList(List<String> phasesIds) throws ExecutionException, InterruptedException {
        try {
            return phaseRepository.getPhasesFromIds(phasesIds);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
