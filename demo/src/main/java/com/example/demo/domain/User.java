package com.example.demo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userEmail; // 명세서: userEmail

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userAddress;

    @Column(nullable = false)
    private String phoneNumber;

    private String gender; // 선택 항목 (FEMALE, MALE)
    
    private LocalDate birthDate; // 선택 항목 (YYYY-MM-DD)

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @jakarta.persistence.Column(columnDefinition = "boolean default true")
    private boolean isActive = true;

    // --- 생성자 ---
    public User() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // --- Getter & Setter ---
    public Long getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public String getPassword() { return password; }
    public String getUserName() { return userName; }
    public String getUserAddress() { return userAddress; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getGender() { return gender; }
    public LocalDate getBirthDate() { return birthDate; }

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setPassword(String password) { this.password = password; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setUserAddress(String userAddress) { this.userAddress = userAddress; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public LocalDateTime getCreatedAt() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCreatedAt'");
    }

    public Long getUserId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserId'");
    }

    public void setRole(Role role) {
        // TODO Auto-generated method stub
        this.role = role;
    }

    public void setEmail(String get) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRole() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRole'");
    }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public void setCreatedAt(LocalDateTime now) {
        this.createdAt = createdAt;
    }

    public void setRole(Object string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRole'");
    }
}