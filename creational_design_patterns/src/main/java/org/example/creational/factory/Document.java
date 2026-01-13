package org.example.creational.factory;

public interface Document {
    void open();
    void save();
    void display();
    void setContent(String content);
    String getContent();
}
