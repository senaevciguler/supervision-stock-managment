package com.kiwi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiwi.dto.IngredientDto;
import com.kiwi.dto.MeasurementDto;
import com.kiwi.entities.Ingredient;
import com.kiwi.entities.Measurement;
import com.kiwi.services.MeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MeasurementControllerTest {

    @Mock
    MeasurementService measurementService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    MeasurementController measurementController;

    private static final ObjectMapper om = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(measurementController).build();
    }

    @Test
    void findAll() throws Exception {
        //given
        List<Measurement> measurements = List.of(Measurement.builder()
                .id(1L)
                .name("test")
                .build());
        given(measurementService.findAll()).willReturn(measurements);
        //when
        mockMvc.perform(get("/api/v1/measurement"))
                .andExpect(status().isOk());
        //then
        then(measurementService).should().findAll();
    }

    @Test
    void getById() throws Exception {
        //given
        given(measurementService.findById(1L)).willReturn(new Measurement());
        //when
        mockMvc.perform(get("/api/v1/measurement/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(measurementService).should().findById(1L);
    }

    @Test
    void save() throws Exception {
        Measurement measurement = Measurement.builder()
                .id(1L)
                .name("test")
                .build();
        //given
        given(measurementService.save(any())).willReturn(measurement);
        //when
        mockMvc.perform(post("/api/v1/measurement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(new Measurement())))
                .andExpect(status().isCreated());
        //then
        then(measurementService).should().save(any());
    }

    @Test
    void update() throws Exception {

        //given
        MeasurementDto measurementDto = MeasurementDto.builder()
                .id(1L)
                .name("test")
                .build();

        Measurement measurement = Measurement.builder()
                .name("test")
                .build();
        given(measurementService.update(any(), anyLong()))
                .willReturn(Optional.ofNullable(measurement));
        //when
        mockMvc.perform(put("/api/v1/measurement/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(measurementDto)))
                .andExpect(status().isCreated());
        //then
        then(measurementService).should().update(any(), anyLong());

    }

    @Test
    void deleteById() throws Exception {
        //when
        mockMvc.perform(delete("/api/v1/measurement/{id}", 1L))
                .andExpect(status().isOk());
        //then
        then(measurementService).should().delete(anyLong());
    }
}
