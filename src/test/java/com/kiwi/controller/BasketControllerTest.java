package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Address;
import com.kiwi.entities.Basket;
import com.kiwi.services.AddressService;
import com.kiwi.services.BasketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        mockMvc.perform(get("api/v1/basket"))
                .andExpect(status().isOk());

        //then
        then(basketService).should().findAll();
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void updateBasket() {
    }

    @Test
    void delete() {
    }
}