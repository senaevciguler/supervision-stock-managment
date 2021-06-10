package com.kiwi.service;

import com.kiwi.entities.Basket;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.BasketRepository;
import com.kiwi.services.implementation.BasketServiceImpl;
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

@DisplayName("Basket Service Tests")
@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {
    @Mock
    private BasketRepository basketRepository;

    @InjectMocks
    private BasketServiceImpl service;

    private Basket basket;

    @BeforeEach
    void setUp() {
        basket = Basket.builder()
                .id(1L)
                .quantity(1L)
                .build();
    }

    @DisplayName("Basket Find All")
    @Test
    void findAll() {
        //given
        given(basketRepository.findAll()).willReturn((List.of(basket)));
        //when
        List<Basket> foundBasket = service.findAll();
        //then
        then(basketRepository).should().findAll();
        assertThat(foundBasket).hasSize(1);
    }

    @DisplayName("Basket Find By Id")
    @Test
    void findById() {
        //given
        given(basketRepository.findById(anyLong())).willReturn(Optional.of(basket));
        //when
        Optional<Basket> foundBasket = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(basketRepository).should().findById(anyLong());
        assertThat(foundBasket).isPresent();
    }

    @DisplayName("Basket Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(basketRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Basket Create")
    @Test
    void create() {
        //given
        given(basketRepository.save(any(Basket.class))).willReturn(basket);
        //when
        Basket savedBasket = service.save(new Basket());
        //then
        then(basketRepository).should().save(any(Basket.class));
        assertThat(savedBasket).isNotNull();
    }

    @DisplayName("Basket Update")
    @Test
    void update() {
        //given
        given(basketRepository.findById(anyLong())).willReturn(Optional.of(basket));
        given(basketRepository.save(any(Basket.class))).willReturn(basket);
        //when
        Optional<Basket> savedBasket = service.update(basket, anyLong());
        //then
        then(basketRepository).should().findById(anyLong());
        then(basketRepository).should().save(any(Basket.class));
        assertThat(savedBasket).isPresent();
    }
}
