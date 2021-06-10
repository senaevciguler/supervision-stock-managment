package com.kiwi.service;

import com.kiwi.entities.Measurement;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.MeasurementRepository;
import com.kiwi.services.implementation.MeasurementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("Measurement Service Tests")
@ExtendWith(MockitoExtension.class)
public class MeasurementServiceTest {
    @Mock
    private MeasurementRepository measurementRepository;

    @InjectMocks
    private MeasurementServiceImpl service;

    private Measurement measurement;

    @BeforeEach
    void setUp() {
        measurement = Measurement.builder()
                .id(1L)
                .name("test")
                .build();
    }

    @DisplayName("Measurement Find All")
    @Test
    void findAll() {
        //given
        given(measurementRepository.findAll()).willReturn((List.of(measurement)));
        //when
        List<Measurement> foundMeasurement = service.findAll();
        //then
        then(measurementRepository).should().findAll();
        assertThat(foundMeasurement).hasSize(1);
    }

    @DisplayName("Measurement Find By Id")
    @Test
    void findById() {
        //given
        given(measurementRepository.findById(anyLong())).willReturn(Optional.of(measurement));
        //when
        Optional<Measurement> foundMeasurement = Optional.ofNullable(service.findById(anyLong()));
        //then
        then(measurementRepository).should().findById(anyLong());
        assertThat(foundMeasurement).isPresent();
    }

    @DisplayName("Measurement Find By Id Not Found")
    @Test
    void findById_not_found() {
        //given
        given(measurementRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        assertThrows(
                NotFoundException.class,
                () -> service.findById(anyLong()));
    }

    @DisplayName("Measurement Create")
    @Test
    void create() {
        //given
        given(measurementRepository.save(any(Measurement.class))).willReturn(measurement);
        //when
        Measurement savedMeasurement = service.save(new Measurement());
        //then
        then(measurementRepository).should().save(any(Measurement.class));
        assertThat(savedMeasurement).isNotNull();
    }

    @DisplayName("Ingredient Update")
    @Test
    void update() {
        //given
        given(measurementRepository.findById(anyLong())).willReturn(Optional.of(measurement));
        given(measurementRepository.save(any(Measurement.class))).willReturn(measurement);
        //when
        Optional<Measurement> savedMeasurement = service.update(measurement, anyLong());
        //then
        then(measurementRepository).should().findById(anyLong());
        then(measurementRepository).should().save(any(Measurement.class));
        assertThat(savedMeasurement).isPresent();
    }

}
