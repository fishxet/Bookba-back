package com.example.bookba.dto;

public class BookUpdateDto extends BookCreateDto {
    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}