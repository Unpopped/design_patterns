package org.example.structural.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.example.structural.dto.BookDto;
import org.example.structural.entity.Book;
import org.example.structural.utils.BookMapper;
import org.example.structural.service.BookService;
import org.example.structural.service.LibraryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class LibraryController {

    private final BookService bookService;
    private final LibraryFacade libraryFacade;

    @Autowired
    public LibraryController(BookService bookService, LibraryFacade libraryFacade) {
        this.bookService = bookService;
        this.libraryFacade = libraryFacade;
    }

    @Operation(summary = "Retrieve all books", description = "Returns a list of all books in the library as BookDto objects")
    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a book by ID", description = "Provide an ID to lookup a specific book in the library")
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@Parameter(description = "ID of the book to retrieve") @PathVariable Long id) {
        return bookService.getBookById(id)
                .map(book -> ResponseEntity.ok(BookMapper.toDTO(book)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new book", description = "Adds a new book to the library and returns the saved BookDto object")
    @PostMapping
    public BookDto addBook(@Parameter(description = "BookDto object to be added") @RequestBody BookDto bookDto) {
        Book book = BookMapper.toEntity(bookDto);
        Book savedBook = bookService.addBook(book);
        return BookMapper.toDTO(savedBook);
    }

    @Operation(summary = "Update an existing book", description = "Updates an existing book by ID with new information from the BookDto object")
    @PutMapping("/{id}")
    public BookDto updateBook(
            @Parameter(description = "ID of the book to update") @PathVariable Long id,
            @Parameter(description = "Updated BookDto object") @RequestBody BookDto updatedBookDto) {
        Book updatedBook = BookMapper.toEntity(updatedBookDto);
        Book savedBook = bookService.updateBook(id, updatedBook);
        return BookMapper.toDTO(savedBook);
    }

    @Operation(summary = "Delete a book by ID", description = "Deletes the book with the specified ID from the library")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@Parameter(description = "ID of the book to delete") @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get featured books", description = "Returns a list of featured books using the Decorator pattern")
    @GetMapping("/featured")
    public List<BookDto> getFeaturedBooks() {
        return libraryFacade.getFeaturedBooks()
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get bestseller books", description = "Returns a list of bestseller books with premium pricing using the Decorator pattern")
    @GetMapping("/bestsellers")
    public List<BookDto> getBestsellers() {
        return libraryFacade.getBestsellers()
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Find books by category", description = "Returns books filtered by category using the Facade pattern")
    @GetMapping("/category/{category}")
    public List<BookDto> findByCategory(@Parameter(description = "Category to filter by") @PathVariable String category) {
        return libraryFacade.findBooksByCategory(category)
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }
}