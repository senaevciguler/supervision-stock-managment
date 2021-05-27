package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Address;
import com.kiwi.entities.Product;
import com.kiwi.entities.Store;
import com.kiwi.services.StoreService;
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
class StoreControllerTest {

    @Mock
    StoreService storeService;

    @InjectMocks
    StoreController storeController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(storeController).build();
    }

    @Test
    void findAll() throws Exception {
        //given
        List<Store> store = List.of(Store.builder()
                .id(1L)
                .name("test")
                .products(List.of(Product.builder().build()))
                .address(Address.builder().build())
                .build());
        given(storeService.findAll()).willReturn(store);
        //when
        mockMvc.perform(get("/api/v1/store"))
                .andExpect(status().isOk());
        //then
        then(storeService).should().findAll();
    }

    @Test
    void getById() throws Exception {
        //given
        given(storeService.findById(1L)).willReturn(new Store());
        //when
        mockMvc.perform(get("/api/v1/store/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(storeService).should().findById(1L);
    }

    @Test
    void save() throws Exception {
        Store store = Store.builder()
                .id(1L)
                .name("test")
                .products(List.of(Product.builder().build()))
                .address(Address.builder().build())
                .build();
        //given
        given(storeService.save(any())).willReturn(store);
        //when
        mockMvc.perform(post("/api/v1/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Store())))
                .andExpect(status().isCreated());
        //then
        then(storeService).should().save(any());
    }

    @Test
    void update() throws Exception {
        //given
        Store store = Store.builder()
                .name("test")
                .products(List.of(Product.builder().build()))
                .address(Address.builder().build())
                .build();
        given(storeService.update(store, 1L))
                .willReturn(Optional.ofNullable(store));
        //when
        mockMvc.perform(put("/api/v1/store/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(store)))
                .andExpect(status().isCreated());
        //then
        then(storeService).should().update(store, 1L);
    }

    @Test
    void deleteById() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/store/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(storeService).should().delete(anyLong());
    }
}