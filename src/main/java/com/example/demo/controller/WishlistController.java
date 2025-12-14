package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Book;
import com.example.demo.domain.User;
import com.example.demo.domain.Wishlist;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WishlistRepository;

@RestController
@RequestMapping("/api/wishlists") // 명세서 주소 반영
public class WishlistController {

    @Autowired private WishlistRepository wishlistRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;

    // 37번: 즐겨찾기 등록 (POST)
    @PostMapping
    public Map<String, Object> addWishlist(@RequestBody Map<String, Object> params) {
        // 명세서엔 bookId만 있지만, 테스트를 위해 userId도 같이 받도록 구현했습니다.
        Long userId = Long.valueOf(params.get("userId").toString());
        Long bookId = Long.valueOf(params.get("bookId").toString());

        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setBook(book);

        wishlistRepository.save(wishlist);

        Map<String, Object> payload = new HashMap<>();
        payload.put("wishlistId", wishlist.getId());

        return response(true, "즐겨찾기 등록 성공", payload);
    }

    // 38번: 즐겨찾기 목록 조회 (GET)
    @GetMapping
    public Map<String, Object> getWishlists() {
        // 'deletedAt'이 없는(삭제되지 않은) 것만 조회
        List<Map<String, Object>> list = wishlistRepository.findAll().stream()
                .filter(w -> w.getDeletedAt() == null) 
                .map(w -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("wishlistId", w.getId());
                    m.put("bookId", w.getBook().getBookId());
                    m.put("userId", w.getUser().getId());
                    return m;
                })
                .collect(Collectors.toList());

        return response(true, "즐겨찾기 목록 조회 성공", list);
    }

    // 39번: 즐겨찾기 소프트 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteWishlist(@PathVariable Long id) {
        Wishlist wishlist = wishlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 즐겨찾기입니다."));

        // 진짜 삭제(delete)가 아니라 날짜만 기록(Soft Delete)
        wishlist.setDeletedAt(LocalDateTime.now());
        wishlistRepository.save(wishlist);

        return response(true, "즐겨찾기 삭제(Soft Delete) 성공", null);
    }

    // 공통 응답 만들기
    private Map<String, Object> response(boolean success, String message, Object payload) {
        Map<String, Object> res = new HashMap<>();
        res.put("isSuccess", success);
        res.put("message", message);
        if (payload != null) res.put("payload", payload);
        return res;
    }
}