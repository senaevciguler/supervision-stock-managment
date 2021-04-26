package com.kiwi.controller;

import com.kiwi.entities.Favourite;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.implementation.FavouriteServiceImpl;
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
@RequestMapping("api/v1")
public class FavouriteController {

    @Autowired
    FavouriteServiceImpl favouriteService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/favourite")
    public List<Favourite> findALl(){
        return favouriteService.findAll();
    }

    @GetMapping("/favourite/{id}")
    public Favourite findById(@PathVariable long id){
        return favouriteService.findById(id);

    }

    @PostMapping("/favourite")
    ResponseEntity<Object> save(@RequestBody Favourite favourite){
       Favourite savedFavourite = favouriteService.save(favourite);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedFavourite.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/favourite/{id}")

    ResponseEntity<Object> update(@RequestBody Favourite favourite, @PathVariable long id){
        Optional<Favourite> favouriteOptional = Optional.ofNullable(favouriteService.findById(id));

        if(!favouriteOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        favourite.setId(id);
        Favourite savedFavourite = favouriteService.save(favourite);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedFavourite.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/favourite/{id}")

    public void deleteById(@PathVariable long id){
        favouriteService.delete(id);
    }

}
