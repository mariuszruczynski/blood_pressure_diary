package com.example.blood_pressure_diary.service;

import com.example.blood_pressure_diary.entity.MeasurementEntity;
import com.example.blood_pressure_diary.model.Measurement;
import com.example.blood_pressure_diary.repository.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final PdfService pdfService;
    private final UserUtils userUtils;

    public MeasurementService(MeasurementRepository measurementRepository, PdfService pdfService, UserUtils userUtils) {
        this.measurementRepository = measurementRepository;
        this.pdfService = pdfService;
        this.userUtils = userUtils;
    }


    public byte[] generatePdfReport(List<MeasurementEntity> list, String startDate, String endDate) throws IOException {
        return pdfService.generatePdfReport(list, startDate, endDate);
    }

    public List<MeasurementEntity> findAllByUserId() {
        List<MeasurementEntity> measurementEntities = measurementRepository.findByUerId(userUtils.getLoggedUserId());
        Collections.reverse(measurementEntities);

        return measurementEntities;
    }

    public List<MeasurementEntity> findByDate(String startDate, String endDate) {
        List<MeasurementEntity> measurementEntities = measurementRepository.findByDate(Date.valueOf(startDate),Date.valueOf(endDate), userUtils.getLoggedUserId());
        Collections.reverse(measurementEntities);

        return measurementEntities;
    }

    public MeasurementEntity findById(Long id) {
        return measurementRepository.find(id);
    }

    public void create(Measurement measurement) {
        MeasurementEntity measurementEntity = new MeasurementEntity();

        measurementEntity.setDate(checkAndSetDate(measurement));
        measurementEntity.setTime(checkAndSetTime(measurement));
        measurementEntity.setRr(measurement.getRr());
        measurementEntity.setPulse(measurement.getPulse());
        measurementEntity.setDescription(measurement.getDescription());
        measurementEntity.setIdUser(userUtils.getLoggedUserId());

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
}

