package org.example.structural.service;

import org.example.structural.entity.Book;

public abstract class BaseBookDecorator implements BookDecorator {
    protected final Book book;

    public BaseBookDecorator(Book book) {
        this.book = book;
    }

    @Override
    public String getTitle() {
        return book.getTitle();
    }

    @Override
    public String getAuthor() {
        return book.getAuthor();
    }

    @Override
    public String getCategory() {
        return book.getCategory();
    }

    @Override
    public double getPrice() {
        return book.getPrice();
    }

    @Override
    public String getDescription() {
        return book.getDescription();
    }

    @Override
    public Book getWrappedBook() {
        return book;
    }
}
