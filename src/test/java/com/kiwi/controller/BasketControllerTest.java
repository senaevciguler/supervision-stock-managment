package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Basket;
import com.kiwi.services.BasketService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BasketControllerTest {

    @Mock
    BasketService basketService;

    @InjectMocks
    BasketController basketController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(basketController).build();
    }

    @Test
    void findAll() throws Exception {
        //given
        List<Basket> basket = List.of(Basket.builder()
                .id(1L)
                .quantity(1L)
                .build());
        given(basketService.findAll()).willReturn(basket);
        //when
        mockMvc.perform(get("/api/v1/basket"))
                .andExpect(status().isOk());
        //then
        then(basketService).should().findAll();
    }

    @Test
    void getBasketById() throws Exception {
        //given
        given(basketService.findById(1L)).willReturn(new Basket());
        //when
        mockMvc.perform(get("/api/v1/basket/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(basketService).should().findById(1L);
    }

    @Test
    void save() throws Exception {
        Basket basket = Basket.builder()
                .id(1L)
                .quantity(1L)
                .build();
        //given
        given(basketService.save(any())).willReturn(basket);
        //when
        mockMvc.perform(post("/api/v1/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Basket())))
                .andExpect(status().isCreated());
        //then
        then(basketService).should().save(any());
    }

    @Test
    void updateBasket() throws Exception {
        //given
        Basket basket = Basket.builder()
                .quantity(1L)
                .build();
        given(basketService.update(basket, 1L))
                .willReturn(Optional.ofNullable(basket));
        //when
        mockMvc.perform(put("/api/v1/basket/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(basket)))
                .andExpect(status().isCreated());
        //then
        then(basketService).should().update(basket, 1L);
    }

    @Test
    void deleteById() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/basket/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(basketService).should().delete(anyLong());
    }
}