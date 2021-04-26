package com.kiwi.services.implementation;

import com.kiwi.entities.Ingredient;
import com.kiwi.repositories.IngredientRepository;
import com.kiwi.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;


    @Override
    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient findById(long id) {
        return ingredientRepository.findById(id).get();
    }

    @Override
    public Optional<Ingredient> update(Ingredient ingredient, long id) {
        return ingredientRepository.findById(id).map(ingredientUpdate ->{
            ingredientUpdate.setName(ingredient.getName());

            return ingredientRepository.save(ingredientUpdate);
        });
    }

    @Override
    public void delete(long id) {

        ingredientRepository.deleteById(id);
    }
}
