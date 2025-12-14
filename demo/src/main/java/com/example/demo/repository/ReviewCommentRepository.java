package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.ReviewComment;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {
}