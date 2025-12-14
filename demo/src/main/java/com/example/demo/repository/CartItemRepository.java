package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}