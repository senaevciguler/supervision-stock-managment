package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.entities.Role;
import com.kiwi.services.RoleService;
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
class RoleControllerTest {

    @Mock
    RoleService roleService;

    @InjectMocks
    RoleController roleController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    void findAll() throws Exception {
        //given
        List<Role> role = List.of(Role.builder()
                .id(1L)
                .name("test")
                .build());
        given(roleService.findAll()).willReturn(role);
        //when
        mockMvc.perform(get("/api/v1/role"))
                .andExpect(status().isOk());
        //then
        then(roleService).should().findAll();
    }

    @Test
    void getById() throws Exception {
        //given
        given(roleService.findById(1L)).willReturn(new Role());
        //when
        mockMvc.perform(get("/api/v1/role/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(roleService).should().findById(1L);

    }

    @Test
    void save() throws Exception {
        Role role = Role.builder()
                .id(1L)
                .name("test")
                .build();
        //given
        given(roleService.save(any())).willReturn(role);
        //when
        mockMvc.perform(post("/api/v1/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Role())))
                .andExpect(status().isCreated());
        //then
        then(roleService).should().save(any());
    }

    @Test
    void update() throws Exception {
        //given
        Role role = Role.builder()
                .name("test")
                .build();
        given(roleService.update(role, 1L))
                .willReturn(Optional.ofNullable(role));
        //when
        mockMvc.perform(put("/api/v1/role/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(role)))
                .andExpect(status().isCreated());

        //then
        then(roleService).should().update(role, 1L);
    }

    @Test
    void deleteById() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/role/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(roleService).should().delete(anyLong());
    }
}