package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.domain.CartItem;
import com.example.demo.domain.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;

    // 장바구니 담기 (POST /api/cart)
    @PostMapping
    public Map<String, Object> addToCart(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long bookId = Long.valueOf(params.get("bookId").toString());
        Integer quantity = (Integer) params.get("quantity");

        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        CartItem item = new CartItem();
        item.setUser(user);
        item.setBook(book);
        item.setQuantity(quantity);

        cartItemRepository.save(item);

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", true);
        response.put("message", "장바구니에 담겼습니다.");
        return response;
    }
}