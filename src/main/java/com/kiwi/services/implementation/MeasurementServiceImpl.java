package com.kiwi.services.implementation;

import com.kiwi.entities.Measurement;
import com.kiwi.repositories.MeasurementRepository;
import com.kiwi.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    @Override
    public Measurement save(Measurement measurement) {
        return measurementRepository.save(measurement);
    }

    @Override
    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Override
    public Measurement findById(long id) {
        return measurementRepository.findById(id).get();
    }

    @Override
    public Optional<Measurement> update(Measurement measurement, long id) {
        return measurementRepository.findById(id).map(measurementUpdated ->{
            measurementUpdated.setName(measurement.getName());

            return measurementRepository.save(measurementUpdated);
        });
    }

    @Override
    public void delete(long id) {

        measurementRepository.deleteById(id);
    }
}
