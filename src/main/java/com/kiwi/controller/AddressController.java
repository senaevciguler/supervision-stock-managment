package com.kiwi.controller;

import com.kiwi.entities.Address;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.implementation.AddressServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@RestController
@RequestMapping("/api/v1")
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;

    @Autowired
    private MessageSource messageSource;

   @GetMapping("/address")
    public List<Address> getAllAddress(){return addressService.findAll();}

    @PostMapping("/address")
    public ResponseEntity<Object> save( @RequestBody Address address){
       Address savedAddress = addressService.save(address);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedAddress.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
    @GetMapping("/address/{id}")
    public Address getAddressById(@PathVariable long id){return addressService.findById(id);}

    @PutMapping("/address/{id}")
    public ResponseEntity<Object> updateAddress(@PathVariable long id, @RequestBody Address address){
       Optional<Address> addressOptional = Optional.ofNullable(addressService.findById(id));

        if(!addressOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        address.setId(id);
        Address savedAddress = addressService.save(address);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedAddress.getId())
                .toUri();

        return ResponseEntity.created(location).build(); }

    @DeleteMapping("/address/{id}")

    public void deleteAddress(@PathVariable long id){ addressService.deleteById(id);}
}
