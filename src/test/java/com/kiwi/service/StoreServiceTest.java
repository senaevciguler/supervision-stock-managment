package com.kiwi.service;

import com.kiwi.entities.Address;
import com.kiwi.entities.Product;
import com.kiwi.entities.Store;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.StoreRepository;
import com.kiwi.services.implementation.StoreServiceImpl;
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

@DisplayName("Store Service Tests")
@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreServiceImpl service;

    private Store store;

    @BeforeEach
    void setUp() {
        store = Store.builder()
                .id(1L)
                .name("test")
                .products(List.of(Product.builder().build()))
                .address(Address.builder().build())
                .build();
    }

    @DisplayName("Store Find All")
    @Test
    void findAll() {
        //given
        given(storeRepository.findAll()).willReturn((List.of(store)));
        //when
        List<Store> foundStore = service.findAll();
        //then
        then(storeRepository).should().findAll();
        assertThat(foundStore).hasSize(1);
    }

    @DisplayName("Store Find By Id")
    @Test
    void findById() {
        //given
        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        //when
        Optional<Store> foundStore = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(storeRepository).should().findById(anyLong());
        assertThat(foundStore).isPresent();
    }

    @DisplayName("Store Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(storeRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Store Create")
    @Test
    void create() {
        //given
        given(storeRepository.save(any(Store.class))).willReturn(store);
        //when
        Store savedStore = service.save(new Store());
        //then
        then(storeRepository).should().save(any(Store.class));
        assertThat(savedStore).isNotNull();
    }

    @DisplayName("Role Update")
    @Test
    void update() {
        //given
        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        given(storeRepository.save(any(Store.class))).willReturn(store);
        //when
        Optional<Store> savedStore = service.update(store, anyLong());
        //then
        then(storeRepository).should().findById(anyLong());
        then(storeRepository).should().save(any(Store.class));
        assertThat(savedStore).isPresent();
    }
}
