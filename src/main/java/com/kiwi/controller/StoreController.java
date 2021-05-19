package com.kiwi.controller;

import com.kiwi.entities.Store;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.StoreService;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class StoreController {

    @Autowired
    StoreService storeService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/store")
    public List<Store> findAll() {
        return storeService.findAll();
    }

    @GetMapping("/store/{id}")
    public Store findById(@PathVariable long id) {
        return storeService.findById(id);
    }

    @PostMapping("/store")
    ResponseEntity<Object> save(@RequestBody Store store) {
        Store savedStore = storeService.save(store);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStore.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/store/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody Store store) {
        Optional<Store> storeOptional = Optional.ofNullable(storeService.findById(id));

        if (!storeOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        store.setId(id);
        Store savedStore = storeService.save(store);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStore.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/store/{id}")
    public void delete(@PathVariable long id) {
        storeService.delete(id);
    }
}
