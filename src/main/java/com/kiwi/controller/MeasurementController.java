package com.kiwi.controller;

import com.kiwi.entities.Measurement;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.MeasurementService;
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
@RequestMapping("/api/v1")
public class MeasurementController {
    @Autowired
    MeasurementService measurementService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/measurement")
    public List<Measurement> findALl() {
        return measurementService.findAll();
    }

    @GetMapping("/measurement/{id}")
    public Measurement findById(@PathVariable long id) {
        return measurementService.findById(id);

    }

    @PostMapping("/measurement")
    ResponseEntity<Object> save(@RequestBody Measurement measurement) {
        Measurement savedMeasurement = measurementService.save(measurement);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedMeasurement.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/measurement/{id}")
    ResponseEntity<Object> update(@RequestBody Measurement measurement, @PathVariable long id) {
        Optional<Measurement> measurementOptional = Optional.ofNullable(measurementService.findById(id));

        if (!measurementOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        measurement.setId(id);
        Measurement savedMeasurement = measurementService.save(measurement);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedMeasurement.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/measurement/{id}")
    public void deleteById(@PathVariable long id) {
        measurementService.delete(id);
    }
}
