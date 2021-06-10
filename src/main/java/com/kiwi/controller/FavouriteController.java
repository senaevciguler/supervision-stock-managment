package com.kiwi.controller;

import com.kiwi.dto.FavouriteDto;
import com.kiwi.entities.Favourite;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.FavouriteService;
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
@RequestMapping("api/v1")
public class FavouriteController {

    @Autowired
    FavouriteService favouriteService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/favourite")
    public List<FavouriteDto> findALl() {
        return favouriteService.findAll()
                .stream()
                .map(favourite -> modelMapper.map(favourite, FavouriteDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/favourite/{id}")
    public FavouriteDto findById(@PathVariable long id) {
        Favourite favourite = favouriteService.findById(id);
        return modelMapper.map(favourite, FavouriteDto.class);
    }

    @PostMapping("/favourite")
    public ResponseEntity<Object> save(@RequestBody FavouriteDto favouriteDto) {
        Favourite favourite = modelMapper.map(favouriteDto, Favourite.class);

        favourite = favouriteService.save(favourite);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(favourite.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(favourite, FavouriteDto.class));
    }

    @PutMapping("/favourite/{id}")
    public ResponseEntity<Object> update(@RequestBody FavouriteDto favouriteDto, @PathVariable long id) {
        Favourite favourite = modelMapper.map(favouriteDto, Favourite.class);
        Optional<Favourite> favouriteOptional = favouriteService.update(favourite, id);

        if (!favouriteOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(favouriteOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(favouriteOptional, FavouriteDto.class));
    }

    @DeleteMapping("/favourite/{id}")
    public void deleteById(@PathVariable long id) {
        favouriteService.delete(id);
    }
}
