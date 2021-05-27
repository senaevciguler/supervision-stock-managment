package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Basket;
import com.kiwi.entities.Ingredient;
import com.kiwi.services.BasketService;
import com.kiwi.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    IngredientService ingredientService;

    @InjectMocks
    IngredientController ingredientController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }


    @Test
    void findALl() throws Exception {
        //given
        List<Ingredient> ingredients = List.of(Ingredient.builder()
                .id(1L)
                .name("test")
                .build());
        given(ingredientService.findAll()).willReturn(ingredients);

        //when
        mockMvc.perform(get("/api/v1/ingredient"))
                .andExpect(status().isOk());

        //then
        then(ingredientService).should().findAll();
    }

    @Test
    void findById() throws Exception {
        //given
        given(ingredientService.findById(1L)).willReturn(new Ingredient());
        //when
        mockMvc.perform(get("/api/v1/ingredient/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(ingredientService).should().findById(1L);
    }

    @Test
    void save() throws Exception {
        Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .name("test")
                .build();
        //given
        given(ingredientService.save(any())).willReturn(ingredient);
        //when
        mockMvc.perform(post("/api/v1/ingredient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Ingredient())))
                .andExpect(status().isCreated());
        //then
        then(ingredientService).should().save(any());
    }

    @Test
    void update()throws Exception {
        //given
        Ingredient ingredient = Ingredient.builder()
                .name("test")
                .build();
        given(ingredientService.update(ingredient, 1L))
                .willReturn(Optional.ofNullable(ingredient));

        //when
        mockMvc.perform(put("/api/v1/ingredient/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(ingredient)))
                .andExpect(status().isCreated());

        //then
        then(ingredientService).should().update(ingredient, 1L);
    }

    @Test
    void deleteById() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/ingredient/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(ingredientService).should().delete(anyLong());
    }
}