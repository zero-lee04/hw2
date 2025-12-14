package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 모든 컨트롤러의 에러를 여기서 잡음
public class GlobalExceptionHandler {

    // 1. 정보를 못 찾았을 때 (예: 없는 책 조회)
    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class})
    public Map<String, Object> handleNotFound(Exception e) {
        return errorResponse(false, e.getMessage());
    }

    // 2. 알 수 없는 나머지 모든 에러
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleAll(Exception e) {
        e.printStackTrace(); // 서버 로그엔 남김
        return errorResponse(false, "서버 내부 오류가 발생했습니다: " + e.getMessage());
    }

    private Map<String, Object> errorResponse(boolean isSuccess, String message) {
        Map<String, Object> res = new HashMap<>();
        res.put("isSuccess", isSuccess);
        res.put("message", message);
        res.put("result", null);
        return res;
    }
}