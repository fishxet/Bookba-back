package com.example.bookba.service;

import com.example.bookba.dto.*;
import com.example.bookba.model.Book;
import com.example.bookba.repository.AuthorRepository;
import com.example.bookba.repository.BookRepository;
import com.example.bookba.repository.GenreRepository;
import com.example.bookba.spec.BookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    public Page<BookDto> search(
            String title, Integer publicationYear,
            java.util.List<Long> authorIds, java.util.List<Long> genreIds,
            Pageable pageable) {
        var spec = BookSpecification.filter(title, publicationYear, authorIds, genreIds);
        return bookRepository.findAll(spec, pageable)
                .map(this::toDto);
    }

    @Transactional
    public BookDto create(BookCreateDto dto) {
        Book book = new Book();
        applyDto(book, dto);
        return toDto(bookRepository.save(book));
    }

    @Transactional
    public BookDto update(BookUpdateDto dto) {
        Book book = bookRepository.findById(dto.getId()).orElseThrow();
        applyDto(book, dto);
        return toDto(bookRepository.save(book));
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private void applyDto(Book book, BookCreateDto dto) {
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setPublicationYear(dto.getPublicationYear());
        book.setIsbn(dto.getIsbn());
        book.setCoverImageUrl(dto.getCoverImageUrl());
        book.setAuthors(dto.getAuthorIds().stream()
                .map(authorRepository::getReferenceById)
                .collect(Collectors.toSet()));
        book.setGenres(dto.getGenreIds().stream()
                .map(genreRepository::getReferenceById)
                .collect(Collectors.toSet()));
    }

    private BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setIsbn(book.getIsbn());
        dto.setCoverImageUrl(book.getCoverImageUrl());
        dto.setCreatedDate(book.getCreatedDate());
        dto.setLastModifiedDate(book.getLastModifiedDate());
        dto.setAuthors(book.getAuthors().stream()
                .map(a -> new AuthorDto(a.getId(), a.getName()))
                .collect(Collectors.toList()));
        dto.setGenres(book.getGenres().stream()
                .map(g -> new GenreDto(g.getId(), g.getName()))
                .collect(Collectors.toList()));
        return dto;
    }
}