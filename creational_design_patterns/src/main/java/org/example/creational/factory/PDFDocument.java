package org.example.creational.factory;

public class PDFDocument implements Document {
    private String content;

    @Override
    public void open() {
        System.out.println("Opening PDF document...");
    }

    @Override
    public void save() {
        System.out.println("Saving PDF document with Adobe PDF format...");
    }

    @Override
    public void display() {
        System.out.println("Displaying PDF document in PDF viewer:");
        System.out.println("=== PDF Content ===");
        System.out.println(content != null ? content : "[Empty Document]");
        System.out.println("===================");
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}
