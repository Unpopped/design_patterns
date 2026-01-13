package org.example.structural.utils;


import org.example.structural.dto.BookDto;
import org.example.structural.entity.Book;
import org.example.structural.service.BookDecorator;

public class BookMapper {

    public static BookDto toDTO(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .category(book.getCategory())
                .price(book.getPrice())
                .description(book.getDescription())
                .build();
    }

    public static BookDto toDTO(BookDecorator decorator) {
        return BookDto.builder()
                .id(decorator.getWrappedBook().getId())
                .title(decorator.getTitle())
                .author(decorator.getAuthor())
                .category(decorator.getCategory())
                .price(decorator.getPrice())
                .description(decorator.getDescription())
                .build();
    }

    public static Book toEntity(BookDto dto) {
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .build();
    }
}

