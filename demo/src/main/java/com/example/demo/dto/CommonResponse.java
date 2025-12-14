package com.example.demo.dto;

import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private final boolean isSuccess;
    private final String message;
    private final T result;

    public CommonResponse(boolean isSuccess, String message, T result) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.result = result;
    }
}