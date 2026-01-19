package org.example.structural.service;

import org.example.structural.entity.Book;

public class BestsellerBookDecorator extends BaseBookDecorator {
    private static final double BESTSELLER_PRICE_PREMIUM = 1.10;

    public BestsellerBookDecorator(Book book) {
        super(book);
    }

    @Override
    public String getDescription() {
        return "[BESTSELLER] " + super.getDescription();
    }

    @Override
    public double getPrice() {
        return super.getPrice() * BESTSELLER_PRICE_PREMIUM;
    }
}
