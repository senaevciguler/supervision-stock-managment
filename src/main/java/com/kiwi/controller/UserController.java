package com.kiwi.controller;

import com.kiwi.entities.Role;
import com.kiwi.entities.User;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.implementation.UserServiceImpl;
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
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    MessageSource messageSource;

   @GetMapping("/user")
    public List<User> findAll(){
       return userService.findAll();
   }

   @GetMapping("/user/{id}")
    public User findById(@PathVariable long id){
       return userService.findById(id);
   }
   @PostMapping("/user")
    ResponseEntity<Object> save(@RequestBody User user){
       User savedUser = userService.save(user);

       URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
               .toUri();

       return ResponseEntity.created(location).build();
   }


    @PutMapping("/user/{id}")
    ResponseEntity<Object> update(@RequestBody User user, @PathVariable long id){
        Optional<User> userOptional = Optional.ofNullable(userService.findById(id));

        if(!userOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        user.setId(id);
        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/user/{id}")

    public void deleteById(@PathVariable long id){
        userService.delete(id);
    }


}
