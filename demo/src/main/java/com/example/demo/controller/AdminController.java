package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;

    // 1. 전체 회원 조회 (GET /api/admin/users)
    @GetMapping("/users")
    public Map<String, Object> getAllUsers() {
        List<User> users = userRepository.findAll();
        
        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", true);
        response.put("message", "전체 회원 조회 성공");
        response.put("result", users);
        return response;
    }

    // 2. 회원 정지 시키기 (PUT /api/admin/users/{userId}/ban)
    @PutMapping("/users/{userId}/ban")
    public Map<String, Object> banUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        user.setActive(false); // 활성 상태 끄기 (정지)
        userRepository.save(user); // DB 반영

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", true);
        response.put("message", "회원(ID:" + userId + ")이 정지 처리되었습니다.");
        return response;
    }

    // 3. 관리자 대시보드 통계 (GET /api/admin/stats)
    // 필수 요건 "통계/집계" 항목 해결!
    @GetMapping("/stats")
    public Map<String, Object> getDashboardStats() {
        long userCount = userRepository.count(); // 총 회원 수
        long bookCount = bookRepository.count(); // 총 도서 수
        
        // (나중에 OrderRepository 생기면 총 주문 수도 추가 가능)
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userCount);
        stats.put("totalBooks", bookCount);

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", true);
        response.put("message", "통계 조회 성공");
        response.put("result", stats);
        return response;
    }
}