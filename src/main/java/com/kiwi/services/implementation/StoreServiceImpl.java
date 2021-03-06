package com.kiwi.services.implementation;

import com.kiwi.entities.Store;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.StoreRepository;
import com.kiwi.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Override
    public Store save(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store findById(long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Store can not find with id :" + id));
    }

    @Override
    public Optional<Store> update(Store store, long id) {
        return storeRepository.findById(id).map(storeUpdated -> {
            storeUpdated.setName(store.getName());
            storeUpdated.setAddress(store.getAddress());
            storeUpdated.setProducts(store.getProducts());

            return storeRepository.save(storeUpdated);
        });
    }

    @Override
    public Store findByStoreName(String storeName) {
        return storeRepository.findByStoreName(storeName);
    }

    @Override
    public void delete(long id) {
        storeRepository.deleteById(id);
    }
}
