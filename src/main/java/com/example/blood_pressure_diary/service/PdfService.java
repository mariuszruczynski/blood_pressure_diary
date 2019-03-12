package com.example.blood_pressure_diary.service;

import com.example.blood_pressure_diary.entity.MeasurementEntity;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PdfService {
    private final UserUtils userUtils;

    public PdfService(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    public byte[] generatePdfReport(List<MeasurementEntity> measurementList, String startDate, String endDate) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(outputStream);
        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);
        List<Paragraph> paragraphs = new ArrayList<>();

        paragraphs.add(new Paragraph("Meauserments for user: " + userUtils.getLoggedUserName()));
        paragraphs.add((new Paragraph("\n")));
        paragraphs.add((new Paragraph("Date from: " + startDate + " to: " + endDate)));
        paragraphs.add((new Paragraph("")));


        for (MeasurementEntity me : measurementList) {
            paragraphs.add(createNewParagraph(me));
        }

        paragraphs.add((new Paragraph("")));
        paragraphs.add((new Paragraph("print date: " + LocalDate.now().toString())));

        for (Paragraph p : paragraphs) {
            document.add(p);
        }

        IntStream.range(1, pdfDoc.getNumberOfPages() + 1)
                .mapToObj(pdfDoc::getPage)
                .forEach(page -> page.setRotation(0));
        document.close();

        return outputStream.toByteArray();
    }

    private Paragraph createNewParagraph(MeasurementEntity me) {
        return new Paragraph(
                "Date: " +
                        me.getDate().toString()
                        + ", time: "
                        + me.getTime().toString()
                        + ", Rr: " + me.getRr()
                        + ", pulse: "
                        + me.getPulse()
                        + ", desc: "
                        + me.getDescription());
    }
}
