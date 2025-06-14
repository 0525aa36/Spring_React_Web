package com.jaemin_web.demo.controller.dto;

import com.jaemin_web.demo.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.authorName = comment.getAuthor().getUsername();
        this.createdAt = comment.getCreatedAt();
    }
} 