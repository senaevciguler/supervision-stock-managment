package com.kiwi.service;

import com.kiwi.entities.Ingredient;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.IngredientRepository;
import com.kiwi.services.implementation.IngredientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("Ingredient Service Tests")
@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl service;

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = Ingredient.builder()
                .id(1L)
                .name("test")
                .build();
    }

    @DisplayName("Ingredient Find All")
    @Test
    void findAll() {
        //given
        given(ingredientRepository.findAll()).willReturn((List.of(ingredient)));
        //when
        List<Ingredient> foundIngredient = service.findAll();
        //then
        then(ingredientRepository).should().findAll();
        assertThat(foundIngredient).hasSize(1);
    }

    @DisplayName("Ingredient Find By Id")
    @Test
    void findById() {
        //given
        given(ingredientRepository.findById(anyLong())).willReturn(Optional.of(ingredient));
        //when
        Optional<Ingredient> foundIngredient = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(ingredientRepository).should().findById(anyLong());
        assertThat(foundIngredient).isPresent();
    }

    @DisplayName("Ingredient Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(ingredientRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Ingredient Create")
    @Test
    void create() {
        //given
        given(ingredientRepository.save(any(Ingredient.class))).willReturn(ingredient);
        //when
        Ingredient savedIngredient = service.save(new Ingredient());
        //then
        then(ingredientRepository).should().save(any(Ingredient.class));
        assertThat(savedIngredient).isNotNull();
    }

    @DisplayName("Ingredient Update")
    @Test
    void update() {
        //given
        given(ingredientRepository.findById(anyLong())).willReturn(Optional.of(ingredient));
        given(ingredientRepository.save(any(Ingredient.class))).willReturn(ingredient);
        //when
        Optional<Ingredient> savedIngredient = service.update(ingredient, anyLong());
        //then
        then(ingredientRepository).should().findById(anyLong());
        then(ingredientRepository).should().save(any(Ingredient.class));
        assertThat(savedIngredient).isPresent();
    }

}
