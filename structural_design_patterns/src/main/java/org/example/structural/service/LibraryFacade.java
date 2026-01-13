package org.example.structural.service;


import org.example.structural.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LibraryFacade {

    private final BookService bookService;

    @Autowired
    public LibraryFacade(BookService bookService) {
        this.bookService = bookService;
    }

    public Book addBook(Book book) {
        return bookService.addBook(book);
    }

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public List<BookDecorator> getFeaturedBooks() {
        return bookService.getAllBooks().stream()
                .limit(3)
                .map(FeaturedBookDecorator::new)
                .collect(Collectors.toList());
    }

    public List<BookDecorator> getBestsellers() {
        return bookService.getAllBooks().stream()
                .filter(book -> book.getPrice() > 20.0)
                .map(BestsellerBookDecorator::new)
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByCategory(String category) {
        return bookService.findByCategory(category);
    }

    public void deleteBook(Long id) {
        bookService.deleteBook(id);
    }
}
