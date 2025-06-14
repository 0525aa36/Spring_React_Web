package com.jaemin_web.demo.controller.dto;

import com.jaemin_web.demo.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisterRequestDto {
    private String username;
    private String password;
    private User.Gender gender;
    private String email;

    public User toEntity() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setGender(this.gender);
        user.setEmail(this.email);
        return user;
    }
} 