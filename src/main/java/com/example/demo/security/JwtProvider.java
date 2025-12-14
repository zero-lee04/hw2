package com.example.demo.security;

import java.util.Date;

import javax.crypto.SecretKey; // ★ SecretKey 타입으로 변경 (0.12.x 버전 요구사항)

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
    // 비밀키 (실무에선 숨겨야 하지만, 과제니까 간단하게 하드코딩)
    private final String secret = "this-is-very-very-very-very-secret-key-for-homework-assignment";
    
    // Key 타입을 SecretKey로 변경
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes()); 

    // 1. 토큰 생성 (로그인 시 호출)
    public String createToken(Long userId, String role) {
        long validTime = 1000L * 60 * 60; // 1시간 유효
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // 토큰 내용: 유저 ID
                .claim("role", role)                // 토큰 내용: 권한 (USER or ADMIN)
                .setExpiration(new Date(System.currentTimeMillis() + validTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    // 2. 토큰에서 유저 ID 꺼내기
    public Long getUserId(String token) {
        return Long.parseLong(
            Jwts.parser() // ⬅️ parserBuilder 대신 parser()
                .verifyWith(key) // ⬅️ setSigningKey 대신 verifyWith(key)
                .build()
                .parseSignedClaims(token) // ⬅️ parseClaimsJws 대신 parseSignedClaims
                .getPayload()
                .getSubject()
        );
    }

    // 3. 토큰에서 권한 꺼내기
    public String getRole(String token) {
        return Jwts.parser() // ⬅️ parserBuilder 대신 parser()
            .verifyWith(key) // ⬅️ setSigningKey 대신 verifyWith(key)
            .build()
            .parseSignedClaims(token) // ⬅️ parseClaimsJws 대신 parseSignedClaims
            .getPayload()
            .get("role", String.class);
    }

        // 4. 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser() // ⬅️ parserBuilder 대신 parser()
                .verifyWith(key) // ⬅️ setSigningKey 대신 verifyWith(key)
                .build()
                .parseSignedClaims(token); // ⬅️ parseClaimsJws 대신 parseSignedClaims
            return true;
        } catch (Exception e) {
            return false;
        }
}
}