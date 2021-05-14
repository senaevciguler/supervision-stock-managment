package com.kiwi.controller;


import com.kiwi.entities.Product;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.implementation.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/product")
    public List<Product> getAll(){return productService.findAll();}

    @PostMapping("/product")
    public ResponseEntity<Object> save( @RequestBody Product product){
        Product savedProduct = productService.save(product);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }


    @PostMapping("/product/image/{id}")
    public ResponseEntity<Object> createImage(@PathVariable long id,  @ModelAttribute MultipartFile file) throws IOException {
        Product product = productService.findById(id);
        product.setPhoto(file.getBytes());

        Product savedProduct = productService.save(product);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping("/product/{id}")
    public Product getById(@PathVariable long id){return productService.findById(id);}

    @PutMapping("/product/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody Product product){
        Optional<Product> productOptional = Optional.ofNullable(productService.findById(id));

        if(!productOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        product.setId(id);
        Product savedProduct = productService.save(product);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).build(); }

    @DeleteMapping("/product/{id}")

    public void delete(@PathVariable long id){ productService.deleteById(id);}

}
