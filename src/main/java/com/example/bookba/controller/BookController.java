package com.example.bookba.controller;

import com.example.bookba.dto.*;
import com.example.bookba.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public Page<BookDto> list(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer publicationYear,
            @RequestParam(required = false) List<Long> authors,
            @RequestParam(required = false) List<Long> genres,
            Pageable pageable) {
        return bookService.search(title, publicationYear, authors, genres, pageable);
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody BookCreateDto dto) {
        BookDto created = bookService.create(dto);
        return ResponseEntity.created(URI.create("/api/books/" + created.getId())).body(created);
    }

    @PutMapping
    public BookDto update(@RequestBody BookUpdateDto dto) {
        return bookService.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}