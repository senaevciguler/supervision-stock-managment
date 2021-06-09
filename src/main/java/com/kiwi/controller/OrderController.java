package com.kiwi.controller;

import com.kiwi.dto.AddressDto;
import com.kiwi.dto.OrderDto;
import com.kiwi.entities.Address;
import com.kiwi.entities.Order;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.OrderService;
import com.kiwi.services.ProductService;
import com.kiwi.services.StoreService;
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
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    StoreService storeService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/order")
    public List<OrderDto> findALl() {
        return orderService.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/order/{id}")
    public OrderDto findById(@PathVariable long id) {
        Order order = orderService.findById(id);

        return modelMapper.map(order, OrderDto.class);

    }


    @PostMapping("/order")
    ResponseEntity<Object> save(@RequestBody OrderDto orderDto) {

        Order order = modelMapper.map(orderDto, Order.class);

        order = orderService.save(order);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(order.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(order, OrderDto.class));

    }

    @PutMapping("/order/{id}")
    ResponseEntity<Object> update(@RequestBody OrderDto orderDto, @PathVariable long id) {
        Order order = modelMapper.map(orderDto, Order.class);
        Optional<Order> orderOptional = orderService.update(order, id);

        if (!orderOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(orderOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(orderOptional, OrderDto.class));
    }

    @DeleteMapping("/order/{id}")
    public void deleteById(@PathVariable long id) {
        orderService.delete(id);
    }
}
