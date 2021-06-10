package com.kiwi.services.implementation;

import com.kiwi.entities.Product;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.ProductRepository;
import com.kiwi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product can not find with id :" + id));
    }

    @Override
    public List<Product> findByIds(List<Long> ids) {
        return null;
    }

    @Override
    public Optional<Product> update(Product product, long id) {
        return productRepository.findById(id).map(productUpdated -> {
            productUpdated.setName(product.getName());
            productUpdated.setBasket(product.getBasket());
            productUpdated.setCategory(product.getCategory());
            productUpdated.setIngredients(product.getIngredients());
            productUpdated.setPrice(product.getPrice());
            productUpdated.setMeasurement(product.getMeasurement());
            productUpdated.setPhoto(product.getPhoto());
            return productRepository.save(productUpdated);
        });
    }

    @Override
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }
}
