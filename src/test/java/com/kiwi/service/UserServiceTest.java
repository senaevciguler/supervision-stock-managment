package com.kiwi.service;

import com.kiwi.entities.Address;
import com.kiwi.entities.Basket;
import com.kiwi.entities.Favourite;
import com.kiwi.entities.Order;
import com.kiwi.entities.Role;
import com.kiwi.entities.User;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.UserRepository;
import com.kiwi.services.implementation.UserServiceImpl;
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

@DisplayName("User Service Tests")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl service;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
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
    }

    @DisplayName("User Find All")
    @Test
    void findAll() {
        //given
        given(userRepository.findAll()).willReturn((List.of(user)));
        //when
        List<User> foundUser = service.findAll();
        //then
        then(userRepository).should().findAll();
        assertThat(foundUser).hasSize(1);
    }

    @DisplayName("User Find By Id")
    @Test
    void findById() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        //when
        Optional<User> foundUser = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(userRepository).should().findById(anyLong());
        assertThat(foundUser).isPresent();
    }

    @DisplayName("User Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("User Create")
    @Test
    void create() {
        //given
        given(userRepository.save(any(User.class))).willReturn(user);
        //when
        User savedUser = service.save(new User());
        //then
        then(userRepository).should().save(any(User.class));
        assertThat(savedUser).isNotNull();
    }

    @DisplayName("User Update")
    @Test
    void update() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willReturn(user);
        //when
        Optional<User> savedUser = service.update(user, anyLong());
        //then
        then(userRepository).should().findById(anyLong());
        then(userRepository).should().save(any(User.class));
        assertThat(savedUser).isPresent();
    }
}
