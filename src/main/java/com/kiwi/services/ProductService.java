package com.kiwi.services;

import com.kiwi.entities.Product;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product save(Product product);

    List<Product> findAll();

    Product findById(long id);

    Optional<Product> update(Product product, long id);

    void deleteById(long id);

}
