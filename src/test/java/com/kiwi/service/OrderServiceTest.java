package com.kiwi.service;

import com.kiwi.entities.Basket;
import com.kiwi.entities.Order;
import com.kiwi.entities.ProductOrder;
import com.kiwi.entities.Store;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.OrderRepository;
import com.kiwi.services.implementation.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("Order Service Tests")
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl service;

    private Order order;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id(1L)
                .basket(Basket.builder().build())
                .date(new Date())
                .productOrder(List.of(ProductOrder.builder().build()))
                .stores(List.of(Store.builder().build()))
                .build();
    }

    @DisplayName("Order Find All")
    @Test
    void findAll() {
        //given
        given(orderRepository.findAll()).willReturn((List.of(order)));
        //when
        List<Order> foundOrder = service.findAll();
        //then
        then(orderRepository).should().findAll();
        assertThat(foundOrder).hasSize(1);
    }

    @DisplayName("Order Find By Id")
    @Test
    void findById() {
        //given
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));
        //when
        Optional<Order> foundOrder = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(orderRepository).should().findById(anyLong());
        assertThat(foundOrder).isPresent();
    }

    @DisplayName("Order Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(orderRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Order Create")
    @Test
    void create() {
        //given
        given(orderRepository.save(any(Order.class))).willReturn(order);
        //when
        Order savedOrder = service.save(new Order());
        //then
        then(orderRepository).should().save(any(Order.class));
        assertThat(savedOrder).isNotNull();
    }

    @DisplayName("Order Update")
    @Test
    void update() {
        //given
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));
        given(orderRepository.save(any(Order.class))).willReturn(order);
        //when
        Optional<Order> savedOrder = service.update(order, anyLong());
        //then
        then(orderRepository).should().findById(anyLong());
        then(orderRepository).should().save(any(Order.class));
        assertThat(savedOrder).isPresent();
    }
}
