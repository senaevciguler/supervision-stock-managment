package com.kiwi.controller;

import com.kiwi.dto.ProductDto;
import com.kiwi.entities.Product;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.ProductService;
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
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/product")
    public List<ProductDto> getAll() {
        return productService.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/product/{id}")
    public ProductDto getById(@PathVariable long id) {
        Product product = productService.findById(id);

        return modelMapper.map(product, ProductDto.class);
    }

    @PostMapping("/product")
    public ResponseEntity<Object> save(@RequestBody ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);

        product = productService.save(product);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(product, ProductDto.class));
    }


    @PostMapping("/product/image/{id}")
    public ResponseEntity<Object> createImage(@PathVariable long id, @ModelAttribute MultipartFile file) throws IOException {
        Product product = productService.findById(id);
        product.setPhoto(file.getBytes());

        Product savedProduct = productService.save(product);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Optional<Product> productOptional = productService.update(product, id);

        if (!productOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(productOptional, ProductDto.class));
    }

    @DeleteMapping("/product/{id}")
    public void delete(@PathVariable long id) {
        productService.deleteById(id);

    }
}
