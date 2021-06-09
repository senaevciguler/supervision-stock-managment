package com.kiwi.controller;

import com.kiwi.dto.AddressDto;
import com.kiwi.entities.Address;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.AddressService;
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
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/address")
    public List<AddressDto> getAllAddress() {
        return addressService.findAll()
                .stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/address")
    public ResponseEntity<Object> save(@RequestBody AddressDto addressDto) {
        Address address = modelMapper.map(addressDto, Address.class);

        address = addressService.save(address);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(address.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(address, AddressDto.class));
    }

    @GetMapping("/address/{id}")
    public AddressDto getAddressById(@PathVariable long id) {

        Address address = addressService.findById(id);

        return modelMapper.map(address, AddressDto.class);
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<Object> updateAddress(@PathVariable long id, @RequestBody AddressDto addressDto) {
        Address address = modelMapper.map(addressDto, Address.class);
        Optional<Address> addressOptional = addressService.update(address, id);

        if (!addressOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(addressOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(addressOptional, AddressDto.class));
    }

    @DeleteMapping("/address/{id}")
    public void deleteAddress(@PathVariable long id) {
        addressService.deleteById(id);
    }
}
