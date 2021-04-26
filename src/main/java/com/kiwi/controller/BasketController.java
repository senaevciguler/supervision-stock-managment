package com.kiwi.controller;

import com.kiwi.entities.Basket;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.implementation.BasketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@RequestMapping("/api/v1")
public class BasketController {

    @Autowired
    BasketServiceImpl basketService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/basket")
    public List<Basket> findAll(){return basketService.findAll();}

    @GetMapping("/basket/{id}")
    public Basket findById(@PathVariable long id){return basketService.findById(id);}

    @PostMapping("/basket")
    ResponseEntity<Object> save(@RequestBody Basket basket){
        Basket savedBasket = basketService.save(basket);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedBasket.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/basket/{id}")
    public ResponseEntity<Object> updateBasket(@PathVariable long id, @RequestBody Basket basket){
        Optional<Basket> basketOptional = Optional.ofNullable(basketService.findById(id));

        if(!basketOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        basket.setId(id);
        Basket savedBasket = basketService.save(basket);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedBasket.getId())
                .toUri();

        return ResponseEntity.created(location).build(); }

    @DeleteMapping("/basket/{id}")

    public void delete(@PathVariable long id){
        basketService.delete(id);
    }

}
