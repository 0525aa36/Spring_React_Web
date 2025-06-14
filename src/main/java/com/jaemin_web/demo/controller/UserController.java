package com.jaemin_web.demo.controller;

import com.jaemin_web.demo.controller.dto.LoginRequestDto;
import com.jaemin_web.demo.controller.dto.TokenResponseDto;
import com.jaemin_web.demo.controller.dto.UserRegisterRequestDto;
import com.jaemin_web.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequestDto requestDto) {
        userService.register(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        TokenResponseDto tokenResponse = userService.login(requestDto);
        return ResponseEntity.ok(tokenResponse);
    }
} 