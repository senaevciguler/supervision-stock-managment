package com.kiwi.services;

import com.kiwi.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category save(Category category);

    List<Category> findAll();

    Category findById(long id);

    Optional<Category> update(Category category, long id);

    void delete(long id);
}
