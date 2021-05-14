package com.kiwi.controller;

import com.kiwi.entities.Order;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.implementation.OrderServiceImpl;
import com.kiwi.services.implementation.ProductServiceImpl;
import com.kiwi.services.implementation.StoreServiceImpl;
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
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    StoreServiceImpl storeService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/order")
    public List<Order> findALl() {
        return orderService.findAll();
    }

    @GetMapping("/order/{id}")
    public Order findById(@PathVariable long id) {
        return orderService.findById(id);

    }


    @PostMapping("/order")
    ResponseEntity<Object> save(@RequestBody Order order) {
        Order savedOrder = orderService.save(order);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedOrder.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /*
    @PostMapping("/order")
    public Result createOrder(@RequestBody OrderPost order) {
        Order ordered = Order.builder()
                .date(order.getDate())
                .basket(order.getBasket())
                .products((Collection<Product>) productService.findById(order.getProduct()))
                .stores((Collection<Store>) storeService.findByStoreName(order.getStore()))
                .build();

        ordered = orderService.save(ordered);
        return Result.Success.builder()
                .message("Car saved successfully")
                .payload(ordered)
                .build();
    }*/

    @PutMapping("/order/{id}")
    ResponseEntity<Object> update(@RequestBody Order order, @PathVariable long id) {
        Optional<Order> orderOptional = Optional.ofNullable(orderService.findById(id));

        if (!orderOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        order.setId(id);
        Order savedOrder = orderService.save(order);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedOrder.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/order/{id}")
    public void deleteById(@PathVariable long id) {
        orderService.delete(id);
    }
}
