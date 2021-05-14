package com.kiwi.services;

import com.kiwi.entities.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    Store save(Store store);

    List<Store> findAll();

    Store findById(long id);

    Optional<Store> update(Store store, long id);

    Store findByStoreName(String storeName); //TODO not using

    void delete(long id);
}
