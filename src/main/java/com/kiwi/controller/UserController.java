package com.kiwi.controller;

import com.kiwi.dto.AddressDto;
import com.kiwi.dto.UserDto;
import com.kiwi.entities.Address;
import com.kiwi.entities.User;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.UserService;
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
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/user")
    public List<UserDto> findAll() {
        return userService.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")
    public UserDto findById(@PathVariable long id) {
        User user = userService.findById(id);

        return modelMapper.map(user, UserDto.class);
    }

    @PostMapping("/user")
    ResponseEntity<Object> save(@RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        user = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(user, UserDto.class));
    }


    @PutMapping("/user/{id}")
    ResponseEntity<Object> update(@RequestBody UserDto userDto, @PathVariable long id) {
        User user = modelMapper.map(userDto, User.class);
        Optional<User> userOptional = userService.update(user, id);

        if (!userOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(userOptional, UserDto.class));
    }

    @DeleteMapping("/user/{id}")
    public void deleteById(@PathVariable long id) {
        userService.delete(id);
    }
}
