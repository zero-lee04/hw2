package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer quantity; // 몇 권 샀는지
    private Integer price;    // 당시 가격

    public OrderItem() {}

    // Getters & Setters
    public void setOrder(Orders order) { this.order = order; }
    public void setBook(Book book) { this.book = book; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setPrice(Integer price) { this.price = price; }
    
    public Book getBook() { return book; }
    public Integer getQuantity() { return quantity; }
    public Integer getPrice() { return price; }
}