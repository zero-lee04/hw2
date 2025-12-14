package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    // 비밀번호 암호화 도구 (비번을 '1234' 그대로 저장하면 안 됨!)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 보안 끄기 (REST API는 안 씀)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 안 씀
            .authorizeHttpRequests(auth -> auth
                // 1. 로그인, 회원가입, 스웨거, 헬스체크는 누구나 접근 가능
                .requestMatchers("/api/users/signup", "/api/users/login", "/health").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/error").permitAll()
                

                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // 2. 나머지는 로그인한 사람만 접근 가능
                .anyRequest().authenticated()
            )
            // 3. 우리가 만든 JwtFilter를 중간에 끼워넣기
            .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}