package com.kiwi.services.implementation;

import com.kiwi.entities.Basket;
import com.kiwi.repositories.BasketRepository;
import com.kiwi.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

    private static List<Basket> baskets = new ArrayList<>();
    private static long quantityCounter = 0;

    @Autowired
    private BasketRepository basketRepository;

    @Override
    /*
    public Basket save(Basket basket) {
        return basketRepository.save(basket);
    }*/

    public Basket save(Basket basket){
        if(basket.getQuantity()==-1 || basket.getQuantity() == 0){
            basket.setId(++quantityCounter);
        }else{
            delete(basket.getQuantity());
        }
        baskets.add(basket);
        return basket;
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
