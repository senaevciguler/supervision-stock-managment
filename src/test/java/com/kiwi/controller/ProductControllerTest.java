package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.dto.BasketDto;
import com.kiwi.dto.CategoryDto;
import com.kiwi.dto.IngredientDto;
import com.kiwi.dto.MeasurementDto;
import com.kiwi.dto.OrderDto;
import com.kiwi.dto.ProductDto;
import com.kiwi.dto.ProductOrderDto;
import com.kiwi.dto.StoreDto;
import com.kiwi.entities.Basket;
import com.kiwi.entities.Category;
import com.kiwi.entities.Ingredient;
import com.kiwi.entities.Measurement;
import com.kiwi.entities.Order;
import com.kiwi.entities.Product;
import com.kiwi.entities.ProductOrder;
import com.kiwi.entities.Store;
import com.kiwi.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ModelMapper modelMapper;

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
                .photo(om.writeValueAsBytes("test"))
                .measurement(Measurement.builder().build())
                .ingredients(List.of(Ingredient.builder().build()))
                .category(Category.builder().build())
                .basket(Basket.builder().build())
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
    void save() throws Exception {
        //given
        Product product = Product.builder()
                .id(1L)
                .name("test")
                .basket(Basket.builder().build())
                .category(Category.builder().build())
                .price(BigDecimal.valueOf(1))
                .ingredients(List.of(Ingredient.builder().build()))
                .measurement(Measurement.builder().build())
                .photo(om.writeValueAsBytes("test"))
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
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("test")
                .basket(BasketDto.builder().build())
                .category(CategoryDto.builder().build())
                .price(BigDecimal.valueOf(1))
                .ingredients(List.of(IngredientDto.builder().build()))
                .measurement(MeasurementDto.builder().build())
                .photo(om.writeValueAsBytes("test"))
                .price(BigDecimal.valueOf(100L))
                .build();

        Product product = Product.builder()
                .name("test")
                .basket(Basket.builder().build())
                .category(Category.builder().build())
                .price(BigDecimal.valueOf(1))
                .ingredients(List.of(Ingredient.builder().build()))
                .measurement(Measurement.builder().build())
                .photo(om.writeValueAsBytes("test"))
                .price(BigDecimal.valueOf(100L))
                .build();
        given(productService.update(any(), anyLong()))
                .willReturn(Optional.ofNullable(product));
        //when
        mockMvc.perform(put("/api/v1/product/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(productDto)))
                .andExpect(status().isCreated());
        //then
        then(productService).should().update(any(), anyLong());

    }

    @Test
    void deleteById() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/product/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(productService).should().deleteById(anyLong());
    }
}