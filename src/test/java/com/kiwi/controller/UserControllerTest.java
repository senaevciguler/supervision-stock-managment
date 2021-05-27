package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Address;
import com.kiwi.entities.Basket;
import com.kiwi.entities.Favourite;
import com.kiwi.entities.Order;
import com.kiwi.entities.Role;
import com.kiwi.entities.User;
import com.kiwi.services.UserService;
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
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void findAll() throws Exception {
        //given
        List<User> user = List.of(User.builder()
                .id(1L)
                .basket(Basket.builder().build())
                .email("test")
                .favourites(List.of(Favourite.builder().build()))
                .firstName("test")
                .lastName("test")
                .orders(List.of(Order.builder().build()))
                .password("test")
                .role(Role.builder().build())
                .username("test")
                .address(Address.builder().build())
                .build());
        given(userService.findAll()).willReturn(user);

        //when
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk());

        //then
        then(userService).should().findAll();
    }

    @Test
    void getById() throws Exception {
        //given
        given(userService.findById(1L)).willReturn(new User());
        //when
        mockMvc.perform(get("/api/v1/user/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(userService).should().findById(1L);

    }

    @Test
    void save() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .basket(Basket.builder().build())
                .email("test")
                .favourites(List.of(Favourite.builder().build()))
                .firstName("test")
                .lastName("test")
                .orders(List.of(Order.builder().build()))
                .password("test")
                .role(Role.builder().build())
                .username("test")
                .address(Address.builder().build())
                .build();
        //given
        given(userService.save(any())).willReturn(user);
        //when
        mockMvc.perform(post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new User())))
                .andExpect(status().isCreated());
        //then
        then(userService).should().save(any());
    }

    @Test
    void updateBasket() throws Exception {
        //given
        User user = User.builder()
                .basket(Basket.builder().build())
                .email("test")
                .favourites(List.of(Favourite.builder().build()))
                .firstName("test")
                .lastName("test")
                .orders(List.of(Order.builder().build()))
                .password("test")
                .role(Role.builder().build())
                .username("test")
                .address(Address.builder().build())
                .build();
        given(userService.update(user, 1L))
                .willReturn(Optional.ofNullable(user));
        //when
        mockMvc.perform(put("/api/v1/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(user)))
                .andExpect(status().isCreated());
        //then
        then(userService).should().update(user, 1L);
    }

    @Test
    void deleteById() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/user/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(userService).should().delete(anyLong());
    }
}