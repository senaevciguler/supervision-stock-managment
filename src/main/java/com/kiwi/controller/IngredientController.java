package com.kiwi.controller;

import com.kiwi.dto.AddressDto;
import com.kiwi.dto.IngredientDto;
import com.kiwi.entities.Address;
import com.kiwi.entities.Ingredient;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.IngredientService;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/ingredient")
    public List<IngredientDto> findALl() {
        return ingredientService.findAll()
                .stream()
                .map(ingredient -> modelMapper.map(ingredient, IngredientDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/ingredient/{id}")
    public IngredientDto findById(@PathVariable long id) {

        Ingredient ingredient = ingredientService.findById(id);

        return modelMapper.map(ingredient, IngredientDto.class);

    }

    @PostMapping("/ingredient")
    ResponseEntity<Object> save(@RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);

        ingredient = ingredientService.save(ingredient);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(ingredient.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(ingredient, IngredientDto.class));
    }

    @PutMapping("/ingredient/{id}")
    ResponseEntity<Object> update(@RequestBody IngredientDto ingredientDto, @PathVariable long id) {
        Ingredient ingredient = modelMapper.map(ingredientDto, Ingredient.class);
        Optional<Ingredient> ingredientOptional = ingredientService.update(ingredient, id);

        if (!ingredientOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ingredientOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(ingredientOptional, IngredientDto.class));
    }

    @DeleteMapping("/ingredient/{id}")
    public void deleteById(@PathVariable long id) {
        ingredientService.delete(id);
    }
}
