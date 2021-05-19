package com.kiwi.controller;

import com.kiwi.entities.Role;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.RoleService;
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
@RequestMapping("api/v1")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/role")
    public List<Role> findALl() {
        return roleService.findAll();
    }

    @GetMapping("/role/{id}")
    public Role findById(@PathVariable long id) {
        return roleService.findById(id);

    }

    @PostMapping("/role")
    ResponseEntity<Object> save(@RequestBody Role role) {
        Role savedRole = roleService.save(role);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedRole.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/role/{id}")
    ResponseEntity<Object> update(@RequestBody Role role, @PathVariable long id) {
        Optional<Role> roleOptional = Optional.ofNullable(roleService.findById(id));

        if (!roleOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        role.setId(id);
        Role savedRole = roleService.save(role);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedRole.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/role/{id}")
    public void deleteById(@PathVariable long id) {
        roleService.delete(id);
    }
}
