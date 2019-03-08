package com.example.blood_pressure_diary.controller;

import com.example.blood_pressure_diary.entity.MeasurementEntity;
import com.example.blood_pressure_diary.model.Measurement;
import com.example.blood_pressure_diary.service.MeasurementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping(value = {"/measurements"})
    public String getAll(Model model) {
        model.addAttribute("measures", measurementService.findAll());
        return "measurements";
    }

    @GetMapping(value = {"/{id}/measurements"})
    public String getById(@PathVariable Long id, Model model) {

        model.addAttribute("measurement", measurementService.findById(id));
        return "measurementDetails";
    }

    @GetMapping(value = {"/{startDate}/{endDate}/measurementsByDate"})
    public String getByDate(@PathVariable String startDate, @PathVariable String endDate, Model model) {

        model.addAttribute("measures", measurementService.findByDate(startDate, endDate));
        return "measurementsByDate";
    }


    @GetMapping(value = {"/{startDate}/{endDate}/toPdf"})
    public String getByDateAndExportToPdf(@PathVariable String startDate, @PathVariable String endDate, Model model) {

        List<MeasurementEntity> list = measurementService.findByDate(startDate, endDate);
        measurementService.saveToPdf(list);
        return "toPdf";
    }

    @GetMapping(value = {"/addMeasurement"})
    public String addHospital(Model model) {
        model.addAttribute("measurement", new Measurement());
        return "addMeasurement";
    }

    @PostMapping(path = "/addMeasurement")
    public String createHospital(Measurement measurement) {
        measurementService.create(measurement);
        return "redirect:/measurements";
    }
}
