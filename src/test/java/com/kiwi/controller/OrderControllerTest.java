package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.dto.BasketDto;
import com.kiwi.dto.MeasurementDto;
import com.kiwi.dto.OrderDto;
import com.kiwi.dto.ProductOrderDto;
import com.kiwi.dto.StoreDto;
import com.kiwi.entities.Basket;
import com.kiwi.entities.Measurement;
import com.kiwi.entities.Order;
import com.kiwi.entities.Product;
import com.kiwi.entities.ProductOrder;
import com.kiwi.entities.Store;
import com.kiwi.services.OrderService;
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
public class OrderControllerTest {

    @Mock
    OrderService orderService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    OrderController orderController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void findAll() throws Exception {
        //given
        List<Order> order = List.of(Order.builder()
                .id(1L)
                .basket(Basket.builder().build())
                .date(new Date())
                .productOrder(List.of(ProductOrder.builder().build()))
                .stores(List.of(Store.builder().build()))
                .build());
        given(orderService.findAll()).willReturn(order);
        //when
        mockMvc.perform(get("/api/v1/order"))
                .andExpect(status().isOk());
        //then
        then(orderService).should().findAll();
    }

    @Test
    void getById() throws Exception {
        //given
        given(orderService.findById(1L)).willReturn(new Order());
        //when
        mockMvc.perform(get("/api/v1/order/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(orderService).should().findById(1L);

    }

    @Test
    void save() throws Exception {
        //given
        Order order = Order.builder()
                .id(1L)
                .basket(Basket.builder().build())
                .date(new Date())
                .productOrder(List.of(ProductOrder.builder().build()))
                .stores(List.of(Store.builder().build()))
                .build();
        //given
        given(orderService.save(any())).willReturn(order);
        //when
        mockMvc.perform(post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Order())))
                .andExpect(status().isCreated());
        //then
        then(orderService).should().save(any());
    }

    @Test
    void update() throws Exception {

        //given
        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .basket(BasketDto.builder().build())
                .date(new Date())
                .productOrder(List.of(ProductOrderDto.builder().build()))
                .stores(List.of(StoreDto.builder().build()))
                .build();

        Order order = Order.builder()
                .basket(Basket.builder().build())
                .date(new Date())
                .productOrder(List.of(ProductOrder.builder().build()))
                .stores(List.of(Store.builder().build()))
                .build();
        given(orderService.update(any(), anyLong()))
                .willReturn(Optional.ofNullable(order));
        //when
        mockMvc.perform(put("/api/v1/order/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(orderDto)))
                .andExpect(status().isCreated());
        //then
        then(orderService).should().update(any(), anyLong());

    }

    @Test
    void deleteById() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/order/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(orderService).should().delete(anyLong());
    }
}
