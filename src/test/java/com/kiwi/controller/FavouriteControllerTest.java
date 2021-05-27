package com.kiwi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Basket;
import com.kiwi.entities.Favourite;
import com.kiwi.entities.Product;
import com.kiwi.services.BasketService;
import com.kiwi.services.FavouriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
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
class FavouriteControllerTest {

    @Mock
    FavouriteService favouriteService;

    @InjectMocks
    FavouriteController categoryController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void findALl() throws Exception{
        //given
        Product product = Product.builder()
                .id(1L)
                .name("test")
                .price(BigDecimal.valueOf(100L))
                .build();
        //given
        List<Favourite> favourites = List.of(Favourite.builder()
                .id(1L)
                .product(product)
                .build());
        given(favouriteService.findAll()).willReturn(favourites);

        //when
        mockMvc.perform(get("/api/v1/favourite"))
                .andExpect(status().isOk());

        //then
        then(favouriteService).should().findAll();
    }

    @Test
    void findById() throws Exception {
        //given
        given(favouriteService.findById(1L)).willReturn(new Favourite());
        //when
        mockMvc.perform(get("/api/v1/favourite/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(favouriteService).should().findById(1L);
    }

    @Test
    void save() throws Exception {
        //given
        Product product = Product.builder()
                .id(1L)
                .name("test")
                .price(BigDecimal.valueOf(100L))
                .build();
        //given
        Favourite favourite = Favourite.builder()
                .id(1L)
                .product(product)
                .build();
        //given
        given(favouriteService.save(any())).willReturn(favourite);

        //when
        mockMvc.perform(post("/api/v1/favourite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Favourite())))
                .andExpect(status().isCreated());
        //then
        then(favouriteService).should().save(any());
    }

    @Test
    void update() throws Exception {
        //given
        Product product = Product.builder()
                .id(1L)
                .name("test")
                .price(BigDecimal.valueOf(100L))
                .build();
        //given
        Favourite favourite = Favourite.builder()
                .product(product)
                .build();
        given(favouriteService.update(favourite, 1L))
                .willReturn(Optional.ofNullable(favourite));

        //when
        mockMvc.perform(put("/api/v1/favourite/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(favourite)))
                .andExpect(status().isCreated());

        //then
        then(favouriteService).should().update(favourite, 1L);
    }

    @Test
    void deleteById() throws Exception{
        //when
        mockMvc.perform(delete("/api/v1/favourite/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(favouriteService).should().delete(anyLong());
    }
}