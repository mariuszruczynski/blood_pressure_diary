package com.example.blood_pressure_diary.service;

import com.example.blood_pressure_diary.entity.MeasurementEntity;
import com.example.blood_pressure_diary.model.Measurement;
import com.example.blood_pressure_diary.repository.MeasurementRepository;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final PdfService pdfService;

    public MeasurementService(MeasurementRepository measurementRepository, PdfService pdfService) {
        this.measurementRepository = measurementRepository;
        this.pdfService = pdfService;
    }


    public List<MeasurementEntity> findAll() {
        List<MeasurementEntity> measurementEntities = measurementRepository.findAll();
        Collections.reverse(measurementEntities);

        return measurementEntities;
    }

    public List<MeasurementEntity> findByDate(String startDate, String endDate) {
        return measurementRepository.findByDate(startDate, endDate);
    }

    public MeasurementEntity findById(Long id){
        return measurementRepository.find(id);
    }

    public void create(Measurement measurement) {
        MeasurementEntity measurementEntity = new MeasurementEntity();

        measurementEntity.setDate(checkAndSetDate(measurement));
        measurementEntity.setTime(checkAndSetTime(measurement));
        measurementEntity.setRr(measurement.getRr());
        measurementEntity.setPulse(measurement.getPulse());
        measurementEntity.setDescription(measurement.getDescription());
        measurementEntity.setIdUser(1L);

        measurementRepository.save(measurementEntity);
    }

    private LocalTime checkAndSetTime(Measurement measurement) {
        if (isNull(measurement.getTime())) {
            return LocalTime.now();
        }
        return measurement.getTime();
    }

    private LocalDate checkAndSetDate(Measurement measurement) {
        if (measurement.getDate().isEmpty()) {
            return LocalDate.now();
        }
        return LocalDate.parse(measurement.getDate());
    }


    public void saveToPdf(List<MeasurementEntity> measurementsList) {

        try {
            pdfService.exportToPdf(measurementsList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }








}

