package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Product;
import com.kiwi.services.ProductService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController controller;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll() throws Exception {
        //given
        List<Product> products = List.of(Product.builder()
                .id(1L)
                .name("test")
                .price(BigDecimal.valueOf(100L))
                .build());

        given(productService.findAll()).willReturn(products);

        //when
        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk());

        //then
        then(productService).should().findAll();
    }

    @Test
    void findById() throws Exception {
        //given
        given(productService.findById(1L)).willReturn(new Product());

        //when
        mockMvc.perform(get("/api/v1/product/{id}", 1L))
                .andExpect(status().isOk());

        //then
        then(productService).should().findById(1L);
    }

    @Test
    void create() throws Exception {
        //given
        Product product = Product.builder()
                .name("test")
                .price(BigDecimal.valueOf(100L))
                .build();

        given(productService.save(any())).willReturn(product);

        //when
        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Product())))
                .andExpect(status().isCreated());

        //then
        then(productService).should().save(any());
    }

    @Test
    void update() throws Exception {
        //given
        Product product = Product.builder()
                .id(1L)
                .name("test")
                .price(BigDecimal.valueOf(100L))
                .build();

        /*Product product = Product.builder()
                .id(1L)
                .name("test")
                .price(BigDecimal.valueOf(100L))
                .build();*/

        /*ProductRequest productRequest = ProductRequest.builder()
                .product(productDto)
                .build();*/

        given(productService.update(any(), any())).willReturn(java.util.Optional.ofNullable(product));

        //when
        mockMvc.perform(post("/api/v1/product/{id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(product)))
                .andExpect(status().isCreated());

        //then
        then(productService).should().update(any(), any());
    }


    @Test
    void delete() throws Exception {
        /*
        //given
        Product productDeleteRequest = Product.builder().id(1L).build();

        given(productService.deleteById(anyLong())).willReturn(new Product());

        //when
        mockMvc.perform(post("/api/v1/product/{id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(productDeleteRequest)))
                .andExpect(status().isOk());

        //then
        then(productService).should().deleteById(anyLong());*/
    }
}