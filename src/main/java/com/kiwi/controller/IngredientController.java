package com.kiwi.controller;


import com.kiwi.entities.Ingredient;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.implementation.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api/v1")
public class IngredientController {

    @Autowired
    IngredientServiceImpl ingredientService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/ingredient")
    public List<Ingredient> findALl(){
        return ingredientService.findAll();
    }

    @GetMapping("/ingredient/{id}")
    public Ingredient findById(@PathVariable long id){
        return ingredientService.findById(id);

    }

    @PostMapping("/ingredient")
    ResponseEntity<Object> save(@RequestBody Ingredient ingredient){
        Ingredient savedIngredient = ingredientService.save(ingredient);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedIngredient.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/ingredient/{id}")
    ResponseEntity<Object> update(@RequestBody Ingredient ingredient, @PathVariable long id){
        Optional<Ingredient> ingredientOptional = Optional.ofNullable(ingredientService.findById(id));

        if(!ingredientOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        ingredient.setId(id);
        Ingredient savedIngredient = ingredientService.save(ingredient);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedIngredient.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/ingredient/{id}")

    public void deleteById(@PathVariable long id){
        ingredientService.delete(id);
    }
}
