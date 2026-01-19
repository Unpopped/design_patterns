package org.example.creational.factory;

public class WordDocument implements Document {
    private String content;

    @Override
    public void open() {
        System.out.println("Opening Word document...");
    }

    @Override
    public void save() {
        System.out.println("Saving Word document with .docx format...");
    }

    @Override
    public void display() {
        System.out.println("Displaying Word document in Microsoft Word:");
        System.out.println("=== Word Content ===");
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
