package com.kiwi.controller;


import com.kiwi.entities.Address;
import com.kiwi.entities.Stock;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.implementation.AddressServiceImpl;
import com.kiwi.services.implementation.StockServiceImpl;
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
public class StockController {


    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/stock")
    public List<Stock> findAll(){return stockService.findAll();}

    @PostMapping("/stock")
    public ResponseEntity<Object> save( @RequestBody Stock stock){
        Stock savedStock = stockService.save(stock);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStock.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
    @GetMapping("/stock/{id}")
    public Stock findById(@PathVariable long id){return stockService.findById(id);}

    @PutMapping("/stock/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody Stock stock){
        Optional<Stock> stockOptional = Optional.ofNullable(stockService.findById(id));

        if(!stockOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        stock.setId(id);
        Stock savedStock = stockService.save(stock);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStock.getId())
                .toUri();

        return ResponseEntity.created(location).build(); }

    @DeleteMapping("/stock/{id}")

    public void deleteById(@PathVariable long id){ stockService.delete(id);}
}
