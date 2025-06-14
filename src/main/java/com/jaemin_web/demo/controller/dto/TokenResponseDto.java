package com.jaemin_web.demo.controller.dto;

import lombok.Getter;

@Getter
public class TokenResponseDto {
    private String token;
    private String username;

    public TokenResponseDto(String token, String username) {
        this.token = token;
        this.username = username;
    }
} 