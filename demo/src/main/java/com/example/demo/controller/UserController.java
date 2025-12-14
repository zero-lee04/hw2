package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtProvider; // 아까 만든 JwtProvider 임포트

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider; // 토큰 생성기 주입

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화 도구 주입

    // 1번: 회원가입 (POST /api/users)
    @PostMapping("/users")
    public Map<String, Object> signup(@RequestBody User user) {
        // 1. 이메일 중복 체크 (DB 최적화 버전)
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
             throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 2. 비밀번호 암호화 (보안 필수!)
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        // 3. 기본 권한 설정 (일반 유저)
        user.setRole("ROLE_USER");

        // 4. 저장
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", true);
        response.put("message", "회원가입에 성공했습니다.");
        response.put("result", user); 
        return response;
    }

    // 로그인 (POST /api/login) - JWT 버전
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String email = params.get("userEmail");
        String password = params.get("password");

        // 1. 이메일로 사용자 찾기
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호 확인 (암호화된 비번과 비교해야 함)
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        // 3. 로그인 성공 시 -> JWT 토큰 생성!
        String token = jwtProvider.createToken(user.getId(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", true);
        response.put("message", "로그인 성공");
        
        // 중요: 이제 userId 대신 'token'을 줍니다.
        response.put("token", token); 
        response.put("userId", user.getId()); // 필요하다면 같이 줘도 됨
        
        return response;
    }
}