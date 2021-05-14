package com.kiwi.services.implementation;

import com.kiwi.entities.Stock;
import com.kiwi.repositories.StockRepository;
import com.kiwi.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepository stockRepository;

    @Override
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(long id) {
        return stockRepository.findById(id).get();
    }

    @Override
    public Optional<Stock> update(Stock stock, long id) {
        return stockRepository.findById(id).map(stockUpdated -> {
            stockUpdated.setName(stock.getName());
            stockUpdated.setProducts(stock.getProducts());
            stockUpdated.setQuantity(stock.getQuantity());

            return stockRepository.save(stockUpdated);
        });
    }

    @Override
    public void delete(long id) {
        stockRepository.deleteById(id);
    }
}
