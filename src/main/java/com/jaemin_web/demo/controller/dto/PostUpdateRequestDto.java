package com.jaemin_web.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostUpdateRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 500, message = "내용은 500자를 초과할 수 없습니다.")
    private String content;

    private boolean removeImage = false;
} 