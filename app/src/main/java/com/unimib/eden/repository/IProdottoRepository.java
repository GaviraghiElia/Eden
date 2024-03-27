package com.unimib.eden.repository;

import com.unimib.eden.model.Prodotto;

import java.util.List;

public interface IProdottoRepository {
    List<Prodotto> getAllProdotti();

    void deleteProdotto(Prodotto prodotto);

    void insert(Prodotto prodotto);
}
