package com.example.demo.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_REVIEW_USER") // FK 이름 명시
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(
        name = "book_id", 
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_REVIEW_BOOK") // FK 이름 명시
    )
    private Book book;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 댓글 내용

    @Column(nullable = false)
    private Integer rating; // 평점 (1~5)

    private LocalDateTime createdAt;
    
    // 명세서 스타일: 소프트 삭제를 위한 필드
    private LocalDateTime deletedAt; 

    public Review() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // --- Getter & Setter ---
    public Long getReviewId() { return reviewId; }
    public User getUser() { return user; }
    public Book getBook() { return book; }
    public String getContent() { return content; }
    public Integer getRating() { return rating; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }

    public void setUser(User user) { this.user = user; }
    public void setBook(Book book) { this.book = book; }
    public void setContent(String content) { this.content = content; }
    public void setRating(Integer rating) { this.rating = rating; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    public void setCreatedAt(LocalDateTime now) {
        this.createdAt = now;
    }
}