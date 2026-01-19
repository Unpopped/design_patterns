package org.example.creational;

import org.example.creational.builder.Car;
import org.example.creational.factory.Document;
import org.example.creational.service.CarDocumentService;

public class CarManagementApp {

    public static void main(String[] args) {
        CarDocumentService documentService = new CarDocumentService();

        Car car = new Car.Builder()
                .engine("V6")
                .transmission("Automatic")
                .color("Silver")
                .gps(true)
                .safetyPackage(true)
                .build();

        System.out.println(car);
        System.out.println();

        Document pdfDoc = documentService.generateCarDocument(car, "PDF");
        pdfDoc.display();
        pdfDoc.save();
    }
}
