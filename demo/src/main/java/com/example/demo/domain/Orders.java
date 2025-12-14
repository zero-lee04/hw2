package com.example.demo.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders { // 'Order'는 예약어라 'Orders' 사용

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String status; // 예: "ORDERED", "CANCELLED"
    private Integer totalPrice; // 총 주문 금액
    private LocalDateTime orderedAt;

    // 주문에 포함된 책들
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Orders() {}

    @PrePersist
    protected void onCreate() {
        this.orderedAt = LocalDateTime.now();
        this.status = "ORDERED";
    }

    // Helper: 주문 상품 추가
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }

    // Getters & Setters
    public Long getOrderId() { return orderId; }
    public User getUser() { return user; }
    public String getStatus() { return status; }
    public Integer getTotalPrice() { return totalPrice; }
    public List<OrderItem> getOrderItems() { return orderItems; }

    public void setUser(User user) { this.user = user; }
    public void setStatus(String status) { this.status = status; }
    public void setTotalPrice(Integer totalPrice) { this.totalPrice = totalPrice; }
}