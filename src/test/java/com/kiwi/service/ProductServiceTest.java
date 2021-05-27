package com.kiwi.service;

import com.kiwi.entities.Basket;
import com.kiwi.entities.Category;
import com.kiwi.entities.Ingredient;
import com.kiwi.entities.Measurement;
import com.kiwi.entities.Product;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.ProductRepository;
import com.kiwi.services.implementation.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("Product Service Tests")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Test")
                .price(ZERO)
                .basket(Basket.builder().build())
                .ingredients(List.of(Ingredient.builder().build()))
                .category(Category.builder().build())
                .measurement(Measurement.builder().build())
                .photo(new byte[0])
                .build();
    }

    @DisplayName("Product Find All")
    @Test
    void findAll() {
        //given
        given(productRepository.findAll()).willReturn((List.of(product)));
        //when
        List<Product> foundProducts = service.findAll();
        //then
        then(productRepository).should().findAll();
        assertThat(foundProducts).hasSize(1);
    }

    @DisplayName("Product Find By Id")
    @Test
    void findById() {
        //given
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        //when
        Optional<Product> foundProduct = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(productRepository).should().findById(anyLong());
        assertThat(foundProduct).isPresent();
    }

    @DisplayName("Product Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Product Create")
    @Test
    void create() {
        //given
        given(productRepository.save(any(Product.class))).willReturn(product);
        //when
        Product savedProduct = service.save(new Product());
        //then
        then(productRepository).should().save(any(Product.class));
        assertThat(savedProduct).isNotNull();
    }

    @DisplayName("Product Update")
    @Test
    void update() {
        //given
        given(productRepository.findById(anyLong())).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);
        //when
        Optional<Product> savedProduct = service.update( product, anyLong());
        //then
        then(productRepository).should().findById(anyLong());
        then(productRepository).should().save(any(Product.class));
        assertThat(savedProduct).isPresent();
    }
   }