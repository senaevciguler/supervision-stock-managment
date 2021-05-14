package com.kiwi.services;

import com.kiwi.entities.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {

    Ingredient save(Ingredient ingredient);

    List<Ingredient> findAll();

    Ingredient findById(long id);

    Optional<Ingredient> update(Ingredient ingredient, long id);

    void delete(long id);
}
