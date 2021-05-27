package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Product;
import com.kiwi.entities.Stock;
import com.kiwi.services.StockService;
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
class StockControllerTest {

    @Mock
    StockService stockService;

    @InjectMocks
    StockController stockController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }

    @Test
    void findAll() throws Exception {
        //given
        List<Stock> stock = List.of(Stock.builder()
                .id(1L)
                .name("test")
                .quantity(1L)
                .products(List.of(Product.builder().build()))
                .build());
        given(stockService.findAll()).willReturn(stock);
        //when
        mockMvc.perform(get("/api/v1/stock"))
                .andExpect(status().isOk());
        //then
        then(stockService).should().findAll();
    }

    @Test
    void getById() throws Exception {
        //given
        given(stockService.findById(1L)).willReturn(new Stock());
        //when
        mockMvc.perform(get("/api/v1/stock/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(stockService).should().findById(1L);

    }

    @Test
    void save() throws Exception {
        Stock stock = Stock.builder()
                .id(1L)
                .name("test")
                .products(List.of(Product.builder().build()))
                .quantity(1L)
                .build();
        //given
        given(stockService.save(any())).willReturn(stock);
        //when
        mockMvc.perform(post("/api/v1/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Stock())))
                .andExpect(status().isCreated());
        //then
        then(stockService).should().save(any());
    }

    @Test
    void update() throws Exception {
        Stock stock = Stock.builder()
                .name("test")
                .products(List.of(Product.builder().build()))
                .quantity(1L)
                .build();
        given(stockService.update(stock, 1L))
                .willReturn(Optional.ofNullable(stock));
        //when
        mockMvc.perform(put("/api/v1/stock/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(stock)))
                .andExpect(status().isCreated());
        //then
        then(stockService).should().update(stock, 1L);
    }

    @Test
    void deleteById() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/stock/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(stockService).should().delete(anyLong());
    }
}