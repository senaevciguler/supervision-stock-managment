package com.kiwi.controller;

import com.kiwi.dto.CategoryDto;
import com.kiwi.entities.Category;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/category")
    public List<CategoryDto> findAll() {
        return categoryService.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{id}")
    public CategoryDto findById(@PathVariable long id) {
        Category category = categoryService.findById(id);

        return modelMapper.map(category, CategoryDto.class);

    }

    @PostMapping("/category")
    ResponseEntity<Object> save(@RequestBody CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);

        category = categoryService.save(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(category.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(category, CategoryDto.class));

    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Object> updateBasket(@PathVariable long id, @RequestBody CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Optional<Category> categoryOptional = categoryService.update(category, id);

        if (!categoryOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(categoryOptional, CategoryDto.class));
    }

    @DeleteMapping("/category/{id}")
    public void delete(@PathVariable long id) {
        categoryService.delete(id);
    }
}
