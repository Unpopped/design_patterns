package org.example.structural.service;

import org.example.structural.entity.Book;

public interface BookDecorator {
    String getDescription();
    double getPrice();
    String getTitle();
    String getAuthor();
    String getCategory();
    Book getWrappedBook();
}
