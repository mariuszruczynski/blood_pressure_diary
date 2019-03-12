package com.example.blood_pressure_diary.service;

import com.example.blood_pressure_diary.entity.MeasurementEntity;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PdfService {

    public byte[] generatePdfReport(List<MeasurementEntity> list) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(outputStream);
        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDoc, PageSize.A4.rotate());
        AreaBreak aB = new AreaBreak();
        List<Paragraph> paragraphs = new ArrayList<>();

        for (MeasurementEntity me : list) {
            paragraphs.add(createNewParagraph(me));
        }

        for (Paragraph p : paragraphs) {
            document.add(p);
        }

        document.add(aB);

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
