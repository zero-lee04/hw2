package com.example.demo.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_comments")
public class ReviewComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content; // 댓글 내용

    // 어느 리뷰에 달린 댓글인지 (III-1 리뷰와 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    // 누가 썼는지 (I-1 사용자)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt = LocalDateTime.now();

    public ReviewComment() {}

    // Getters & Setters
    public Long getId() { return id; }
    public String getContent() { return content; }
    public Review getReview() { return review; }
    public User getUser() { return user; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setContent(String content) { this.content = content; }
    public void setReview(Review review) { this.review = review; }
    public void setUser(User user) { this.user = user; }
}