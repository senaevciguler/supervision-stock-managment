package com.kiwi.services;

import com.kiwi.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order save(Order order);

    List<Order> findAll();

    Order findById(long id);

    Optional<Order> update(Order order, long id);

    void delete(long id);
}
