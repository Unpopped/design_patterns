package org.example.creational;

import org.example.creational.builder.Car;

public class CarConfiguration {

    public static void main(String[] args) {

        Car economyCar = new Car.Builder()
                .engine("4-Cylinder")
                .transmission("Manual")
                .color("White")
                .safetyPackage(true)
                .build();

        System.out.println("Economy Car:");
        System.out.println(economyCar);
        System.out.println();

        Car familyCar = new Car.Builder()
                .engine("V6")
                .transmission("Automatic")
                .color("Silver")
                .interior("Fabric")
                .gps(true)
                .safetyPackage(true)
                .build();

        System.out.println("Family Car:");
        System.out.println(familyCar);
        System.out.println();

        Car sportsCar = new Car.Builder()
                .engine("V8")
                .transmission("Automatic")
                .color("Red")
                .interior("Leather")
                .sunroof(true)
                .gps(true)
                .safetyPackage(true)
                .build();

        System.out.println("Sports Car:");
        System.out.println(sportsCar);
    }
}
