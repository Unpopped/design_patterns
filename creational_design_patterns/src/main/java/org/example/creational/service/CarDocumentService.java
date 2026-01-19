package org.example.creational.service;

import org.example.creational.builder.Car;
import org.example.creational.factory.Document;
import org.example.creational.factory.DocumentFactory;

public class CarDocumentService {

    public Document generateCarDocument(Car car, String documentType) {
        Document document = DocumentFactory.createDocument(documentType);
        String content = generateCarContent(car);
        document.setContent(content);
        return document;
    }

    private String generateCarContent(Car car) {
        return "Car Report\n" +
                "Engine: " + car.getEngine() + "\n" +
                "Transmission: " + car.getTransmission() + "\n" +
                "Color: " + car.getColor() + "\n" +
                "Interior: " + (car.getInterior() != null ? car.getInterior() : "Standard") + "\n" +
                "Sunroof: " + (car.isHasSunroof() ? "Yes" : "No") + "\n" +
                "GPS: " + (car.isHasGPS() ? "Yes" : "No") + "\n" +
                "Safety Package: " + (car.isHasSafetyPackage() ? "Yes" : "No");
    }
}
