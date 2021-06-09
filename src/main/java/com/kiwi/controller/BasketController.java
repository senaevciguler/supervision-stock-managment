package com.kiwi.controller;

import com.kiwi.dto.AddressDto;
import com.kiwi.dto.BasketDto;
import com.kiwi.entities.Address;
import com.kiwi.entities.Basket;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.BasketService;
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
public class BasketController {

    @Autowired
    BasketService basketService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/basket")
    public List<BasketDto> findAll() {
        return basketService.findAll()
                .stream()
                .map(basket -> modelMapper.map(basket, BasketDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/basket/{id}")
    public BasketDto findById(@PathVariable long id) {
        Basket basket = basketService.findById(id);

        return modelMapper.map(basket, BasketDto.class);
    }

    @PostMapping("/basket")
    ResponseEntity<Object> save(@RequestBody BasketDto basketDto) {

        Basket basket = modelMapper.map(basketDto, Basket.class);

        basket = basketService.save(basket);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(basket.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(basket, AddressDto.class));

    }

    @PutMapping("/basket/{id}")
    public ResponseEntity<Object> updateBasket(@PathVariable long id, @RequestBody BasketDto basketDto) {
        Basket basket = modelMapper.map(basketDto, Basket.class);
        Optional<Basket> basketOptional = basketService.update(basket, id);

        if (!basketOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(basketOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(basketOptional, BasketDto.class));

    }

    @DeleteMapping("/basket/{id}")
    public void delete(@PathVariable long id) {
        basketService.delete(id);
    }
}
