package com.kiwi.service;

import com.kiwi.entities.Category;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.CategoryRepository;
import com.kiwi.services.implementation.CategoryServiceImpl;
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

@DisplayName("Category Service Tests")
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl service;

    private Category category;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("test")
                .build();
    }

    @DisplayName("Category Find All")
    @Test
    void findAll() {
        //given
        given(categoryRepository.findAll()).willReturn((List.of(category)));
        //when
        List<Category> foundCategory = service.findAll();
        //then
        then(categoryRepository).should().findAll();
        assertThat(foundCategory).hasSize(1);
    }

    @DisplayName("Category Find By Id")
    @Test
    void findById() {
        //given
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));
        //when
        Optional<Category> foundCategory = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(categoryRepository).should().findById(anyLong());
        assertThat(foundCategory).isPresent();
    }

    @DisplayName("Category Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(categoryRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Category Create")
    @Test
    void create() {
        //given
        given(categoryRepository.save(any(Category.class))).willReturn(category);
        //when
        Category savedCategory = service.save(new Category());
        //then
        then(categoryRepository).should().save(any(Category.class));
        assertThat(savedCategory).isNotNull();
    }

    @DisplayName("Category Update")
    @Test
    void update() {
        //given
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));
        given(categoryRepository.save(any(Category.class))).willReturn(category);
        //when
        Optional<Category> savedCategory = service.update(category, anyLong());
        //then
        then(categoryRepository).should().findById(anyLong());
        then(categoryRepository).should().save(any(Category.class));
        assertThat(savedCategory).isPresent();
    }
}
