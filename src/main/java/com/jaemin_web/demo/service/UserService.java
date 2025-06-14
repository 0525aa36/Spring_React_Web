package com.jaemin_web.demo.service;

import com.jaemin_web.demo.config.jwt.JwtTokenProvider;
import com.jaemin_web.demo.controller.dto.LoginRequestDto;
import com.jaemin_web.demo.controller.dto.TokenResponseDto;
import com.jaemin_web.demo.controller.dto.UserRegisterRequestDto;
import com.jaemin_web.demo.domain.User;
import com.jaemin_web.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long register(UserRegisterRequestDto requestDto) {
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }
        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setEmail(requestDto.getEmail());
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public TokenResponseDto login(LoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getUsername(),
                        requestDto.getPassword()
                )
        );

        String token = jwtTokenProvider.createToken(authentication);
        return new TokenResponseDto(token, authentication.getName());
    }
} 