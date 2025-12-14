package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Review;
import com.example.demo.domain.ReviewLike;
import com.example.demo.domain.User;
import com.example.demo.repository.ReviewLikeRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/reviews")
public class ReviewLikeController {

    @Autowired private ReviewLikeRepository reviewLikeRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private UserRepository userRepository;

    // 1. 리뷰 좋아요 등록 (POST /api/reviews/{id}/likes)
    @PostMapping("/{id}/likes")
    public Map<String, Object> addLike(@PathVariable Long id, @RequestBody Map<String, Long> params) {
        Long userId = params.get("userId");
        User user = userRepository.findById(userId).orElseThrow();
        Review review = reviewRepository.findById(id).orElseThrow();

        if (reviewLikeRepository.existsByUserAndReview(user, review)) {
            return response(false, "이미 좋아요를 눌렀습니다.", null);
        }

        ReviewLike like = new ReviewLike();
        like.setUser(user);
        like.setReview(review);
        reviewLikeRepository.save(like);

        return response(true, "좋아요 성공", null);
    }

    // 2. 리뷰 좋아요 취소 (DELETE /api/reviews/{id}/likes)
    @Transactional // 삭제할 때 필수
    @DeleteMapping("/{id}/likes")
    public Map<String, Object> removeLike(@PathVariable Long id, @RequestBody Map<String, Long> params) {
        Long userId = params.get("userId");
        User user = userRepository.findById(userId).orElseThrow();
        Review review = reviewRepository.findById(id).orElseThrow();

        reviewLikeRepository.deleteByUserAndReview(user, review);
        return response(true, "좋아요 취소 성공", null);
    }

    private Map<String, Object> response(boolean success, String message, Object data) {
        Map<String, Object> res = new HashMap<>();
        res.put("isSuccess", success);
        res.put("message", message);
        return res;
    }
}