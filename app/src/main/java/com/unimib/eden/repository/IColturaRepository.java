package com.unimib.eden.repository;

import com.unimib.eden.model.Coltura;

import java.util.List;

public interface IColturaRepository {

    List<Coltura> getAllColture();

    void deleteColtura(Coltura coltura);

    void insert(Coltura coltura);
}
