package com.example.bookba.model;
import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Author extends AbstractPersistable {
    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}