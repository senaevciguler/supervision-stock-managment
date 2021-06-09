package com.kiwi.controller;

import com.kiwi.dto.AddressDto;
import com.kiwi.dto.StoreDto;
import com.kiwi.entities.Address;
import com.kiwi.entities.Store;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.StoreService;
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
public class StoreController {

    @Autowired
    StoreService storeService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/store")
    public List<StoreDto> findAll() {
        return storeService.findAll()
                .stream()
                .map(store -> modelMapper.map(store, StoreDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/store/{id}")
    public StoreDto findById(@PathVariable long id) {
        Store store = storeService.findById(id);

        return modelMapper.map(store, StoreDto.class);
    }

    @PostMapping("/store")
    ResponseEntity<Object> save(@RequestBody StoreDto storeDto) {
        Store store = modelMapper.map(storeDto, Store.class);

        store = storeService.save(store);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(store.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(store, StoreDto.class));
    }

    @PutMapping("/store/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody StoreDto storeDto) {
        Store store = modelMapper.map(storeDto, Store.class);
        Optional<Store> storeOptional = storeService.update(store, id);

        if (!storeOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(storeOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(storeOptional, StoreDto.class));
    }

    @DeleteMapping("/store/{id}")
    public void delete(@PathVariable long id) {
        storeService.delete(id);
    }
}
