package com.unimib.eden.repository;

import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFaseRepository {
    List<Fase> getAllFasi();

    List<Fase> getFasiID(List<String> ids) throws ExecutionException, InterruptedException;

    void deleteFase(Fase fase);

    void insert(Fase fase);
}
