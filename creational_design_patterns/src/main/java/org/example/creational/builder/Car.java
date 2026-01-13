package org.example.creational.builder;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Car {
    private final String engine;
    private final String transmission;
    private final String interior;
    private final String color;
    private final boolean hasSunroof;
    private final boolean hasGPS;
    private final boolean hasSafetyPackage;

    private Car(Builder builder) {
        this.engine = builder.engine;
        this.transmission = builder.transmission;
        this.interior = builder.interior;
        this.color = builder.color;
        this.hasSunroof = builder.hasSunroof;
        this.hasGPS = builder.hasGPS;
        this.hasSafetyPackage = builder.hasSafetyPackage;
    }

    public static class Builder {
        private String engine;
        private String transmission;
        private String interior;
        private String color;
        private boolean hasSunroof;
        private boolean hasGPS;
        private boolean hasSafetyPackage;

        public Builder engine(String engine) {
            this.engine = engine;
            return this;
        }

        public Builder transmission(String transmission) {
            this.transmission = transmission;
            return this;
        }

        public Builder interior(String interior) {
            this.interior = interior;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder sunroof(boolean hasSunroof) {
            this.hasSunroof = hasSunroof;
            return this;
        }

        public Builder gps(boolean hasGPS) {
            this.hasGPS = hasGPS;
            return this;
        }

        public Builder safetyPackage(boolean hasSafetyPackage) {
            this.hasSafetyPackage = hasSafetyPackage;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}
