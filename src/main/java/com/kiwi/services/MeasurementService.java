package com.kiwi.services;

import com.kiwi.entities.Measurement;

import java.util.List;
import java.util.Optional;

public interface MeasurementService {
    Measurement save(Measurement measurement);
    List<Measurement> findAll();
    Measurement findById(long id);
    Optional<Measurement> update(Measurement measurement, long id);
    void delete(long id);
}
