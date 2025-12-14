package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/comments") // 목차 IV에 해당
public class ReviewCommentController {

    @Autowired private ReviewCommentRepository commentRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private UserRepository userRepository;

    // 1. 댓글 작성 (POST /api/comments)
    @PostMapping
    public Map<String, Object> createComment(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long reviewId = Long.valueOf(params.get("reviewId").toString());
        String content = (String) params.get("content");

        User user = userRepository.findById(userId).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        ReviewComment comment = new ReviewComment();
        comment.setUser(user);
        comment.setReview(review);
        comment.setContent(content);

        commentRepository.save(comment);

        return response(true, "댓글 작성 성공", null);
    }

    // 2. 댓글 삭제 (DELETE /api/comments/{id})
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return response(true, "댓글 삭제 성공", null);
    }

    private Map<String, Object> response(boolean success, String message, Object data) {
        Map<String, Object> res = new HashMap<>();
        res.put("isSuccess", success);
        res.put("message", message);
        if (data != null) res.put("result", data);
        return res;
    }
}