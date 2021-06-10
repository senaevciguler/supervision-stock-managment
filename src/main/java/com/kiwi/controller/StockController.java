package com.kiwi.controller;

import com.kiwi.dto.StockDto;
import com.kiwi.entities.Stock;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.StockService;
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
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/stock")
    public List<StockDto> findAll() {
        return stockService.findAll()
                .stream()
                .map(stock -> modelMapper.map(stock, StockDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/stock")
    public ResponseEntity<Object> save(@RequestBody StockDto stockDto) {
        Stock stock = modelMapper.map(stockDto, Stock.class);

        stock = stockService.save(stock);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(stock.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(stock, StockDto.class));

    }

    @GetMapping("/stock/{id}")
    public StockDto findById(@PathVariable long id) {
        Stock stock = stockService.findById(id);
        return modelMapper.map(stock, StockDto.class);
    }

    @PutMapping("/stock/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody StockDto stockDto) {
        Stock stock = modelMapper.map(stockDto, Stock.class);
        Optional<Stock> stockOptional = stockService.update(stock, id);

        if (!stockOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(stockOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(stockOptional, StockDto.class));
    }

    @DeleteMapping("/stock/{id}")
    public void deleteById(@PathVariable long id) {
        stockService.delete(id);
    }
}
