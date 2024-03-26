package com.unimib.eden.repository;

import com.unimib.eden.model.Pianta;

import java.util.List;

public interface IPiantaRepository {

    List<Pianta> getAllPiante();

    void deletePianta(Pianta pianta);

    void insert(Pianta pianta);
}
