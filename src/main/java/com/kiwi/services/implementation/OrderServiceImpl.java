package com.kiwi.services.implementation;

import com.kiwi.entities.Order;
import com.kiwi.repositories.OrderRepository;
import com.kiwi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Optional<Order> update(Order order, long id) {
        return orderRepository.findById(id).map(orderUpdated ->{
            orderUpdated.setDate(order.getDate());

            return orderRepository.save(orderUpdated);
        });
    }

    @Override
    public void delete(long id) {orderRepository.deleteById(id);}
}
