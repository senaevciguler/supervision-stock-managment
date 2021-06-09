package com.kiwi.controller;

import com.kiwi.dto.AddressDto;
import com.kiwi.dto.RoleDto;
import com.kiwi.entities.Address;
import com.kiwi.entities.Role;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.RoleService;
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
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/role")
    public List<RoleDto> findALl() {
        return roleService.findAll()
                .stream()
                .map(role -> modelMapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/role/{id}")
    public RoleDto findById(@PathVariable long id) {
        Role role = roleService.findById(id);

        return modelMapper.map(role, RoleDto.class);

    }

    @PostMapping("/role")
    ResponseEntity<Object> save(@RequestBody RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);

        role = roleService.save(role);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(role.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(role, RoleDto.class));
    }

    @PutMapping("/role/{id}")
    ResponseEntity<Object> update(@RequestBody RoleDto roleDto, @PathVariable long id) {
        Role role = modelMapper.map(roleDto, Role.class);
        Optional<Role> roleOptional = roleService.update(role, id);

        if (!roleOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(roleOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(roleOptional, RoleDto.class));
    }

    @DeleteMapping("/role/{id}")
    public void deleteById(@PathVariable long id) {
        roleService.delete(id);
    }
}
