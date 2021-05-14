package com.kiwi.controller;

import com.kiwi.entities.Category;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.implementation.CategoryServiceImpl;
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

@Configuration
@EnableWebSecurity
@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/category")
    public List<Category> findAll(){return categoryService.findAll();}

    @GetMapping("/category/{id}")
    public Category findById(@PathVariable long id){return categoryService.findById(id);}

    @PostMapping("/category")
    ResponseEntity<Object> save(@RequestBody Category category){
        Category savedCategory = categoryService.save(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCategory.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Object> updateBasket(@PathVariable long id, @RequestBody Category category){
        Optional<Category> categoryOptional = Optional.ofNullable(categoryService.findById(id));

        if(!categoryOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        category.setId(id);
        Category savedCategory = categoryService.save(category);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCategory.getId())
                .toUri();

        return ResponseEntity.created(location).build(); }

    @DeleteMapping("/category/{id}")

    public void delete(@PathVariable long id){
        categoryService.delete(id);
    }

}
