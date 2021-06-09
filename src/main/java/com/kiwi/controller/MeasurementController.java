package com.kiwi.controller;

import com.kiwi.dto.AddressDto;
import com.kiwi.dto.MeasurementDto;
import com.kiwi.entities.Address;
import com.kiwi.entities.Measurement;
import com.kiwi.exception.NotFoundException;
import com.kiwi.services.MeasurementService;
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
public class MeasurementController {
    @Autowired
    MeasurementService measurementService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/measurement")
    public List<MeasurementDto> findALl() {
        return measurementService.findAll()
                .stream()
                .map(measurement -> modelMapper.map(measurement, MeasurementDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/measurement/{id}")
    public MeasurementDto findById(@PathVariable long id) {
        Measurement measurement = measurementService.findById(id);

        return modelMapper.map(measurement, MeasurementDto.class);

    }

    @PostMapping("/measurement")
    ResponseEntity<Object> save(@RequestBody MeasurementDto measurementDto) {
        Measurement measurement = modelMapper.map(measurementDto, Measurement.class);

        measurement = measurementService.save(measurement);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(measurement.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(measurement, MeasurementDto.class));
    }

    @PutMapping("/measurement/{id}")
    ResponseEntity<Object> update(@RequestBody MeasurementDto measurementDto, @PathVariable long id) {
        Measurement measurement = modelMapper.map(measurementDto, Measurement.class);
        Optional<Measurement> measurementOptional = measurementService.update(measurement, id);

        if (!measurementOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(measurementOptional.get().getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(modelMapper.map(measurementOptional, MeasurementDto.class));
    }

    @DeleteMapping("/measurement/{id}")
    public void deleteById(@PathVariable long id) {
        measurementService.delete(id);
    }
}
