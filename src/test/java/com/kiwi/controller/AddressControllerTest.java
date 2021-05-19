package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Address;
import com.kiwi.services.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

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
        //Given
        List<Address> adresses = List.of(Address.builder()
                .id(1L)
                .addressLine("test")
                .city("test")
                .country("test")
                .phone("12345")
                .postalCode(1)
                .build());
        given(addressService.findAll()).willReturn(adresses);

        //When


    }

    @Test
    void save() {
    }

    @Test
    void getAddressById() {
    }

    @Test
    void updateAddress() {
    }

    @Test
    void deleteAddress() {
    }
}






