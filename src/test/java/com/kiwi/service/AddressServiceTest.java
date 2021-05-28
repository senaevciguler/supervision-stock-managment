package com.kiwi.service;

import com.kiwi.entities.Address;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.AddressRepository;
import com.kiwi.services.implementation.AddressServiceImpl;
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

@DisplayName("Address Service Tests")
@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl service;

    private Address address;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .id(1L)
                .addressLine("test")
                .city("test")
                .country("test")
                .phone("12345")
                .postalCode(1)
                .build();
    }

    @DisplayName("Address Find All")
    @Test
    void findAll() {
        //given
        given(addressRepository.findAll()).willReturn((List.of(address)));
        //when
        List<Address> foundAddress = service.findAll();
        //then
        then(addressRepository).should().findAll();
        assertThat(foundAddress).hasSize(1);
    }

    @DisplayName("Address Find By Id")
    @Test
    void findById() {
        //given
        given(addressRepository.findById(anyLong())).willReturn(Optional.of(address));
        //when
        Optional<Address> foundProduct = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(addressRepository).should().findById(anyLong());
        assertThat(foundProduct).isPresent();
    }

    @DisplayName("Address Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(addressRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Address Create")
    @Test
    void create() {
        //given
        given(addressRepository.save(any(Address.class))).willReturn(address);
        //when
        Address savedAddress = service.save(new Address());
        //then
        then(addressRepository).should().save(any(Address.class));
        assertThat(savedAddress).isNotNull();
    }

    @DisplayName("Address Update")
    @Test
    void update() {
        //given
        given(addressRepository.findById(anyLong())).willReturn(Optional.of(address));
        given(addressRepository.save(any(Address.class))).willReturn(address);
        //when
        Optional<Address> savedAddress = service.update( address, anyLong());
        //then
        then(addressRepository).should().findById(anyLong());
        then(addressRepository).should().save(any(Address.class));
        assertThat(savedAddress).isPresent();
    }
}
