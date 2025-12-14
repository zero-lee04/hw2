package com.example.demo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.enums.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "books", indexes = {
    @Index(name = "idx_book_title", columnList = "title"), // 제목 검색 최적화
    @Index(name = "idx_book_author", columnList = "author") // 저자 검색 최적화
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId; // 명세서: bookId

    @Column(nullable = false)
    private String title;
    private String category;
    
    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Integer price;

    private LocalDate publicationDate;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Book() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // --- Getter & Setter ---
    public Long getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public String getSummary() { return summary; }
    public String getIsbn() { return isbn; }
    public Integer getPrice() { return price; }
    public LocalDate getPublicationDate() { return publicationDate; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setPrice(Integer price) { this.price = price; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public void setCategory(Category category2) {
        this.category = category;
    }

    public void setCreatedAt(LocalDateTime now) {
        this.createdAt = now;
    }

    public void setUpdatedAt(LocalDateTime plusMinutes) {
        this.updatedAt = updatedAt;
    }
}