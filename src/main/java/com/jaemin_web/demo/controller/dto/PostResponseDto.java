package com.jaemin_web.demo.controller.dto;

import com.jaemin_web.demo.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;
    private String imageUrl;
    private int likes;
    private int dislikes;
    private List<CommentResponseDto> comments;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorName = post.getAuthor().getUsername();
        this.createdAt = post.getCreatedAt();
        this.imageUrl = post.getImageUrl();
        this.likes = post.getLikes();
        this.dislikes = post.getDislikes();
        this.comments = post.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
} 