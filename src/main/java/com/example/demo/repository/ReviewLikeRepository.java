package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.ReviewLike;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    // 이미 좋아요 눌렀는지 확인용
    boolean existsByUserAndReview(com.example.demo.domain.User user, com.example.demo.domain.Review review);
    // 좋아요 취소용
    void deleteByUserAndReview(com.example.demo.domain.User user, com.example.demo.domain.Review review);
}