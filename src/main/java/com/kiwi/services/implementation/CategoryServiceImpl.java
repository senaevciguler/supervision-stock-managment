package com.kiwi.services.implementation;

import com.kiwi.entities.Category;
import com.kiwi.repositories.CategoryRepository;
import com.kiwi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Optional<Category> update(Category category, long id) {
        return categoryRepository.findById(id).map(categoryUpdated ->{
            categoryUpdated.setName(category.getName());

            return categoryRepository.save(categoryUpdated);
        });
    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);

    }
}
