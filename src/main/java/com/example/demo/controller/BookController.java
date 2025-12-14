package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Book;
import com.example.demo.repository.BookRepository;

@RestController
@RequestMapping("/api") // 명세서 공통 경로
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // 18번: 도서 생성 (PATCH /api/admin/books -> 명세서엔 PATCH로 되어있으나 보통 생성은 POST입니다. 명세서 따름)
    @PatchMapping("/admin/books")
    public Map<String, Object> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", true);
        response.put("message", "도서가 성공적으로 등록되었습니다.");
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("bookId", savedBook.getBookId());
        response.put("payload", payload);

        return response;
    }

    // 19번: 도서 단건 조회 (GET /api/public/books/{id})
    @GetMapping("/public/books/{id}")
    public Map<String, Object> getBook(@PathVariable Long id) {
        Object book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("도서를 찾을 수 없습니다."));

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", true);
        response.put("message", "도서 조회에 성공했습니다.");
        response.put("payload", book);

        return response;
    }

    // 2. 도서 목록 조회 (검색 + 페이징 + 정렬)
    @GetMapping("/books")
    public Map<String, Object> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy, // 정렬 기준 (가격, 날짜 등)
            @RequestParam(required = false) String keyword // 검색어
    ) {
        // 정렬 설정 (기본 내림차순)
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        
        Page<Book> bookPage;
        if (keyword == null || keyword.isBlank()) {
            bookPage = bookRepository.findAll(pageable); // 검색어 없으면 전체
        } else {
            bookPage = bookRepository.findByTitleContainingOrAuthorContaining(keyword, keyword, pageable);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("content", bookPage.getContent()); // 실제 데이터
        data.put("totalElements", bookPage.getTotalElements()); // 전체 개수
        data.put("totalPages", bookPage.getTotalPages()); // 전체 페이지 수
        data.put("currentPage", bookPage.getNumber()); // 현재 페이지

        return response(true, "도서 조회 성공", data);
    }

    private Map<String, Object> response(boolean b, String string, Map<String,Object> data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'response'");
    }
}