package com.kiwi.service;

import com.kiwi.entities.Product;
import com.kiwi.entities.Stock;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.StockRepository;
import com.kiwi.services.implementation.StockServiceImpl;
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

@DisplayName("Stock Service Tests")
@ExtendWith(MockitoExtension.class)
public class StockServiceTest {
    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl service;

    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = Stock.builder()
                .id(1L)
                .name("test")
                .product(Product.builder().build())
                .quantity(1)
                .build();
    }

    @DisplayName("Stock Find All")
    @Test
    void findAll() {
        //given
        given(stockRepository.findAll()).willReturn((List.of(stock)));
        //when
        List<Stock> foundStock = service.findAll();
        //then
        then(stockRepository).should().findAll();
        assertThat(foundStock).hasSize(1);
    }

    @DisplayName("Stock Find By Id")
    @Test
    void findById() {
        //given
        given(stockRepository.findById(anyLong())).willReturn(Optional.of(stock));
        //when
        Optional<Stock> foundStock = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(stockRepository).should().findById(anyLong());
        assertThat(foundStock).isPresent();
    }

    @DisplayName("Stock Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(stockRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Stock Create")
    @Test
    void create() {
        //given
        given(stockRepository.save(any(Stock.class))).willReturn(stock);
        //when
        Stock savedStock = service.save(new Stock());
        //then
        then(stockRepository).should().save(any(Stock.class));
        assertThat(savedStock).isNotNull();
    }

    @DisplayName("Role Update")
    @Test
    void update() {
        //given
        given(stockRepository.findById(anyLong())).willReturn(Optional.of(stock));
        given(stockRepository.save(any(Stock.class))).willReturn(stock);
        //when
        Optional<Stock> savedStock = service.update(stock, anyLong());
        //then
        then(stockRepository).should().findById(anyLong());
        then(stockRepository).should().save(any(Stock.class));
        assertThat(savedStock).isPresent();
    }
}
