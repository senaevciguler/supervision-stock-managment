package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.dto.BasketDto;
import com.kiwi.dto.CategoryDto;
import com.kiwi.entities.Basket;
import com.kiwi.entities.Category;
import com.kiwi.services.CategoryService;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    CategoryController categoryController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void findAll() throws Exception {
        //given
        List<Category> categories = List.of(Category.builder()
                .id(1L)
                .name("test")
                .build());
        given(categoryService.findAll()).willReturn(categories);
        //when
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk());
        //then
        then(categoryService).should().findAll();
    }

    @Test
    void findById() throws Exception {
        //given
        given(categoryService.findById(1L)).willReturn(new Category());
        //when
        mockMvc.perform(get("/api/v1/category/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(categoryService).should().findById(1L);
    }

    @Test
    void save() throws Exception {
        Category category = Category.builder()
                .id(1L)
                .name("test")
                .build();
        //given
        given(categoryService.save(any())).willReturn(category);
        //when
        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Category())))
                .andExpect(status().isCreated());
        //then
        then(categoryService).should().save(any());
    }

    @Test
    void updateCategory() throws Exception {
        //given
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("test")
                .build();

        Category category = Category.builder()
                .name("test")
                .build();
        given(categoryService.update(any(), anyLong()))
                .willReturn(Optional.ofNullable(category));
        //when
        mockMvc.perform(put("/api/v1/category/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(categoryDto)))
                .andExpect(status().isCreated());
        //then
        then(categoryService).should().update(any(), anyLong());

    }

    @Test
    void deleteById() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/category/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(categoryService).should().delete(anyLong());
    }
}