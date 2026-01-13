package org.example.creational.factory;

public class DocumentFactory {

    public static Document createDocument(String type) {
        Document doc;
        switch (type) {
            case "PDF":
                doc = new PDFDocument();
                doc.setContent("This is a PDF document with important information.");
                break;
            case "Word":
                doc = new WordDocument();
                doc.setContent("This is a Word document for editing.");
                break;
            case "HTML":
                doc = new HTMLDocument();
                doc.setContent("<html><body><h1>Hello World</h1></body></html>");
                break;
            default:
                throw new IllegalArgumentException("Unknown document type: " + type);
        }
        return doc;
    }
}
