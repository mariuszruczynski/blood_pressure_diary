package com.example.blood_pressure_diary.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.example.blood_pressure_diary.entity.MeasurementEntity;
import com.example.blood_pressure_diary.generator.TimeStampFileNameGenerator;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PdfService {

    private final TimeStampFileNameGenerator timeStampFileNameGenerator;

    public PdfService(TimeStampFileNameGenerator timeStampFileNameGenerator) {
        this.timeStampFileNameGenerator = timeStampFileNameGenerator;
    }

    public void exportToPdf(List<MeasurementEntity> measurementsList) throws FileNotFoundException, DocumentException {

        String directoryPath = "C:/blood_pleasure_diary";
        checkIsDirectoryExistOrCreateNew(directoryPath);

        Document document = new Document();
        PdfPTable table = new PdfPTable(new float[]{2, 2, 2, 1, 5});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Date");
        table.addCell("Time");
        table.addCell("RR");
        table.addCell("Pulse");
        table.addCell("Description");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (MeasurementEntity m : measurementsList) {
            table.addCell(String.valueOf(m.getDate()));
            table.addCell(String.valueOf(m.getTime()));
            table.addCell(m.getRr());
            table.addCell(m.getPulse());
            table.addCell(m.getDescription());
        }
        PdfWriter.getInstance(document, new FileOutputStream(directoryPath + "/" + timeStampFileNameGenerator.generateTimeStampFileName()));
        document.open();
        document.add(table);
        document.close();
        System.out.println("Done");
    }

    private void checkIsDirectoryExistOrCreateNew(String fileName) {
        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Directory created");
        }
    }
}
