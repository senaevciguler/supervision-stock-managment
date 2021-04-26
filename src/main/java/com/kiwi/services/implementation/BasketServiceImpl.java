package com.kiwi.services.implementation;

import com.kiwi.entities.Basket;
import com.kiwi.repositories.BasketRepository;
import com.kiwi.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Override
    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public List<Basket> findAll() {
        return basketRepository.findAll();
    }

    @Override
    public Basket findById(long id) {
        return basketRepository.findById(id).get();
    }

    @Override
    public Optional<Basket> update(Basket basket, long id) {
        return basketRepository.findById(id).map(basketUpdate -> {
            basketUpdate.setQuantity(basket.getQuantity());

            return basketRepository.save(basketUpdate);
        });
    }

    @Override
    public void delete(long id) {
        basketRepository.deleteById(id);

    }
}
