package com.example.demo.repository;

import com.example.demo.domain.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // ★ N+1 방지: JOIN FETCH를 사용하여 Book과 User를 한 번에 조회
    @Query("SELECT r FROM Review r JOIN FETCH r.book JOIN FETCH r.user WHERE r.book.id = :bookId")
    List<Review> findReviewsWithDetailsByBookId(@Param("bookId") Long bookId);
    
    // ★ N+1 방지: @EntityGraph를 사용하여 JPA에게 조인을 요청
    @EntityGraph(attributePaths = {"book", "user"})
    List<Review> findAllByBook_BookId(Long bookId);
}