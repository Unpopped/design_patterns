package org.example.structural.service;

import org.example.structural.entity.Book;

public class FeaturedBookDecorator extends BaseBookDecorator {

    public FeaturedBookDecorator(Book book) {
        super(book);
    }

    @Override
    public String getDescription() {
        return "[FEATURED] " + super.getDescription();
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }
}
