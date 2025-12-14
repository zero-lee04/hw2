package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;

    // 1. 주문 하기 (POST /api/orders)
    @PostMapping
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        List<Map<String, Object>> items = (List<Map<String, Object>>) params.get("items");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Orders order = new Orders();
        order.setUser(user);
        
        int total = 0;
        for (Map<String, Object> itemData : items) {
            Long bookId = Long.valueOf(itemData.get("bookId").toString());
            Integer qty = (Integer) itemData.get("quantity");

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("책 없음"));

            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setQuantity(qty);
            orderItem.setPrice(book.getPrice());

            order.addOrderItem(orderItem);
            total += book.getPrice() * qty;
        }

        order.setTotalPrice(total);
        return response(true, "주문 성공", orderRepository.save(order));
    }

    // 2. 주문 내역 조회 (GET /api/orders)
    @GetMapping
    public Map<String, Object> getOrders() {
        // 실제로는 유저 ID로 필터링해야 함
        return response(true, "주문 목록 조회", orderRepository.findAll());
    }

    // 3. 주문 취소 (DELETE /api/orders/{id})
    @DeleteMapping("/{id}")
    public Map<String, Object> cancelOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return response(true, "주문 취소 성공", null);
    }

    private Map<String, Object> response(boolean success, String message, Object data) {
        Map<String, Object> res = new HashMap<>();
        res.put("isSuccess", success);
        res.put("message", message);
        if (data != null) res.put("result", data);
        return res;
    }
}