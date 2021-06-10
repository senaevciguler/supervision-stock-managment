package com.kiwi.service;


import com.kiwi.entities.Favourite;
import com.kiwi.entities.Product;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.FavouriteRepository;
import com.kiwi.services.implementation.FavouriteServiceImpl;
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

@DisplayName("Favourite Service Tests")
@ExtendWith(MockitoExtension.class)
public class FavouriteServiceTest {
    @Mock
    private FavouriteRepository favouriteRepository;

    @InjectMocks
    private FavouriteServiceImpl service;

    private Favourite favourite;

    @BeforeEach
    void setUp() {
        favourite = Favourite.builder()
                .id(1L)
                .product(Product.builder().build())
                .build();
    }

    @DisplayName("Favourite Find All")
    @Test
    void findAll() {
        //given
        given(favouriteRepository.findAll()).willReturn((List.of(favourite)));
        //when
        List<Favourite> foundFavourite = service.findAll();
        //then
        then(favouriteRepository).should().findAll();
        assertThat(foundFavourite).hasSize(1);
    }

    @DisplayName("Favourite Find By Id")
    @Test
    void findById() {
        //given
        given(favouriteRepository.findById(anyLong())).willReturn(Optional.of(favourite));
        //when
        Optional<Favourite> foundFavourite = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(favouriteRepository).should().findById(anyLong());
        assertThat(foundFavourite).isPresent();
    }

    @DisplayName("Favourite Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(favouriteRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Favourite Create")
    @Test
    void create() {
        //given
        given(favouriteRepository.save(any(Favourite.class))).willReturn(favourite);
        //when
        Favourite savedFavourite = service.save(new Favourite());
        //then
        then(favouriteRepository).should().save(any(Favourite.class));
        assertThat(savedFavourite).isNotNull();
    }

    @DisplayName("Favourite Update")
    @Test
    void update() {
        //given
        given(favouriteRepository.findById(anyLong())).willReturn(Optional.of(favourite));
        given(favouriteRepository.save(any(Favourite.class))).willReturn(favourite);
        //when
        Optional<Favourite> savedFavourite = service.update(favourite, anyLong());
        //then
        then(favouriteRepository).should().findById(anyLong());
        then(favouriteRepository).should().save(any(Favourite.class));
        assertThat(savedFavourite).isPresent();
    }
}
