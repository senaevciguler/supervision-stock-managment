package com.kiwi.services;

import com.kiwi.entities.Basket;

import java.util.List;
import java.util.Optional;

public interface BasketService {

    Basket save(Basket basket);

    List<Basket> findAll();

    Basket findById(long id);

    Optional<Basket> update(Basket basket, Long id);

    void delete(long id);
}
