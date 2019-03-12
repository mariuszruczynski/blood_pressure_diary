package com.example.blood_pressure_diary.controller;

import com.example.blood_pressure_diary.entity.MeasurementEntity;
import com.example.blood_pressure_diary.generator.TimeStampFileNameGenerator;
import com.example.blood_pressure_diary.model.Measurement;
import com.example.blood_pressure_diary.service.MeasurementService;
import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MeasurementController {

    private final MeasurementService measurementService;
    private final TimeStampFileNameGenerator timeStampFileNameGenerator;

    public MeasurementController(MeasurementService measurementService, TimeStampFileNameGenerator timeStampFileNameGenerator) {
        this.measurementService = measurementService;
        this.timeStampFileNameGenerator = timeStampFileNameGenerator;
    }

    @GetMapping(value = {"/measurements"})
    public String getAll(Model model) {
        model.addAttribute("measures", measurementService.findAllByUserId());
        System.out.println(LocalDate.now());
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


    @GetMapping(path="/list")
    public String loadReportPage() {
        return "reportPage";
    }

    @GetMapping(value = {"/{startDate}/{endDate}/toPdf"}, produces = "application/pdf")
    public @ResponseBody void getByDateAndExportToPdf(@PathVariable String startDate, @PathVariable String endDate, Model model, HttpServletResponse response) throws IOException, DocumentException {

        List<MeasurementEntity> list = measurementService.findByDate(startDate, endDate);
        byte[] pdfRaport = measurementService.generatePdfReport(list);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + timeStampFileNameGenerator.generateTimeStampFileName());
        response.setHeader("Content-Length", String.valueOf(pdfRaport.length));
        FileCopyUtils.copy(pdfRaport, response.getOutputStream());

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
