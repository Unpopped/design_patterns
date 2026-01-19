package org.example.creational.factory;

public class HTMLDocument implements Document {
    private String content;

    @Override
    public void open() {
        System.out.println("Opening HTML document...");
    }

    @Override
    public void save() {
        System.out.println("Saving HTML document with .html format...");
    }

    @Override
    public void display() {
        System.out.println("Displaying HTML document in web browser:");
        System.out.println("=== HTML Content ===");
        System.out.println(content != null ? content : "[Empty Document]");
        System.out.println("====================");
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
