package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("서점 프로젝트 API")
                        .description("API 명세서입니다.")
                        .version("1.0.0")); // 🚨 이 부분이 없다면 에러가 납니다!
    }
}