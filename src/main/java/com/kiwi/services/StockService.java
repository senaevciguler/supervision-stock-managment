package com.kiwi.services;

import com.kiwi.entities.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {

    Stock save(Stock stock);
    List<Stock> findAll();
    Stock findById(long id);
    Optional<Stock> update(Stock stock, long id);
    void delete(long id);
}
