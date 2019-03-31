package com.example.blood_pressure_diary.service;

import com.example.blood_pressure_diary.entity.MeasurementEntity;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfService {
    private final UserUtils userUtils;

    public PdfService(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    public byte[] generatePdfReport(List<MeasurementEntity> measurementList, String startDate, String endDate) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(outputStream);
        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4);
        List<Paragraph> paragraphs = new ArrayList<>();
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

        paragraphs.add(new Paragraph("Meauserments for user: " + userUtils.getLoggedUserName()).setFont(font).setFontSize(12));
        paragraphs.add((new Paragraph("")));
        paragraphs.add((new Paragraph("Date from: " + startDate + " to: " + endDate)).setFont(font).setFontSize(10));
        paragraphs.add((new Paragraph("")));

        for (MeasurementEntity me : measurementList) {
            paragraphs.add(createNewParagraph(me).setFont(font).setFontSize(8));
        }

        paragraphs.add((new Paragraph("")));
        paragraphs.add((new Paragraph("print date: " + LocalDate.now().toString()).setFont(font).setFontSize(10)));

        for (Paragraph p : paragraphs) {
            document.add(p);
        }

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
