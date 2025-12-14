package com.example.demo.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity; // 수량

    // 누구 장바구니인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 어떤 책을 담았는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime createdAt;

    public CartItem() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public Integer getQuantity() { return quantity; }
    public User getUser() { return user; }
    public Book getBook() { return book; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setUser(User user) { this.user = user; }
    public void setBook(Book book) { this.book = book; }
}