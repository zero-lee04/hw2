package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;

public class UserDto {

    @Getter
    public static class SignupRequest {
        private String userEmail;
        private String password;
        private String userName;
        private String userAddress;
        private String phoneNumber;
        private String gender; // "MALE" or "FEMALE"
        private LocalDate birthDate;

        public String getUserEmail() { return userEmail; }
        public String getPassword() { return password; }
        public String getUserName() { return userName; }
        public String getUserAddress() { return userAddress; }
        public String getPhoneNumber() { return phoneNumber; }
        public String getGender() { return gender; }
        public LocalDate getBirthDate() { return birthDate; }
    }

    @Getter
    public static class SignupResult {
        private Long userId;
        private String userEmail;
        private LocalDateTime createdAt;

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public SignupResult(Long userId, String userEmail, LocalDateTime createdAt) {
            this.userId = userId;
            this.userEmail = userEmail;
            this.createdAt = createdAt;
        }
    }
}