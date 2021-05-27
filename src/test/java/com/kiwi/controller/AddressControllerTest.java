package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Address;
import com.kiwi.services.AddressService;
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
class AddressControllerTest {

    @Mock
    AddressService addressService;

    @InjectMocks
    AddressController addressController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    void getAllAddress() throws Exception {
        //given
        List<Address> addresses = List.of(Address.builder()
                .id(1L)
                .addressLine("test")
                .city("test")
                .country("test")
                .phone("12345")
                .postalCode(1)
                .build());
        given(addressService.findAll()).willReturn(addresses);

        //when
        mockMvc.perform(get("/api/v1/address"))
                .andExpect(status().isOk());

        //then
        then(addressService).should().findAll();
    }

    @Test
    void getAddressById() throws Exception {
        //given
        given(addressService.findById(1L)).willReturn(new Address());
        //when
        mockMvc.perform(get("/api/v1/address/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(addressService).should().findById(1L);

    }

    @Test
    void save() throws Exception {
        //given
        Address address = Address.builder()
                .addressLine("test")
                .city("test")
                .country("test")
                .phone("123456")
                .postalCode(1234)
                .build();
        given(addressService.save(any())).willReturn(address);

        //when
        mockMvc.perform(post("/api/v1/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Address())))
                .andExpect(status().isCreated());

        //then
        then(addressService).should().save(any());
    }

    @Test
    void updateAddress() throws Exception{
        //given
        Address address = Address.builder()
                .addressLine("test")
                .city("test")
                .country("test")
                .phone("123456")
                .postalCode(1234)
                .build();
        given(addressService.update(address, 1L))
                .willReturn(Optional.ofNullable(address));


        //when
        mockMvc.perform(put("/api/v1/address/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(address)))
                .andExpect(status().isCreated());

        //then
        then(addressService).should().update(address, 1L);
    }


    @Test
    void deleteAddress() throws Exception{
        //when
        mockMvc.perform(delete("/api/v1/address/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(addressService).should().deleteById(anyLong());
    }
}






