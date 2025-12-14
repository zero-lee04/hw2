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
import com.example.demo.domain.Review;
import com.example.demo.domain.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/reviews") // 명세서 스타일 URL
public class ReviewController {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;

    // 댓글(리뷰) 등록 (POST /api/reviews)
    @PostMapping
    public Map<String, Object> createReview(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long bookId = Long.valueOf(params.get("bookId").toString());
        String content = (String) params.get("content");
        Integer rating = (Integer) params.get("rating");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));

        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setContent(content);
        review.setRating(rating);

        reviewRepository.save(review);

        // 응답 데이터 (Payload)
        Map<String, Object> payload = new HashMap<>();
        payload.put("reviewId", review.getReviewId());

        return response(true, "댓글(리뷰) 등록 성공", payload);
    }

    // 댓글(리뷰) 목록 조회 (GET /api/reviews)
    @GetMapping
    public Map<String, Object> getReviews() {
        // 'deletedAt'이 없는(삭제되지 않은) 것만 조회
        List<Map<String, Object>> list = reviewRepository.findAll().stream()
                .filter(r -> r.getDeletedAt() == null)
                .map(r -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("reviewId", r.getReviewId());
                    m.put("bookId", r.getBook().getBookId());
                    m.put("bookTitle", r.getBook().getTitle()); // 책 제목도 같이 보여줌
                    m.put("userName", r.getUser().getUserName()); // 작성자 이름
                    m.put("content", r.getContent());
                    m.put("rating", r.getRating());
                    m.put("createdAt", r.getCreatedAt());
                    return m;
                })
                .collect(Collectors.toList());

        return response(true, "댓글(리뷰) 목록 조회 성공", list);
    }

    // 댓글(리뷰) 소프트 삭제 (DELETE /api/reviews/{id})
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteReview(@PathVariable Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        // 진짜 삭제(delete)가 아니라 날짜만 기록(Soft Delete)
        review.setDeletedAt(LocalDateTime.now());
        reviewRepository.save(review);

        return response(true, "댓글(리뷰) 삭제(Soft Delete) 성공", null);
    }

    // 공통 응답 만들기 (Wishlist와 동일)
    private Map<String, Object> response(boolean success, String message, Object payload) {
        Map<String, Object> res = new HashMap<>();
        res.put("isSuccess", success);
        res.put("message", message);
        if (payload != null) res.put("payload", payload);
        return res;
    }
}