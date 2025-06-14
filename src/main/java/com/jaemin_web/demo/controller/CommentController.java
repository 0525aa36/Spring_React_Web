package com.jaemin_web.demo.controller;

import com.jaemin_web.demo.controller.dto.CommentCreateRequestDto;
import com.jaemin_web.demo.controller.dto.CommentUpdateRequestDto;
import com.jaemin_web.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> createComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long commentId = commentService.createComment(postId, requestDto.getContent(), userDetails.getUsername());
        return ResponseEntity.created(URI.create(String.format("/api/posts/%d/comments/%d", postId, commentId)))
                .body("댓글이 성공적으로 생성되었습니다.");
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            commentService.updateComment(commentId, requestDto.getContent(), userDetails.getUsername());
            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            commentService.deleteComment(commentId, userDetails.getUsername());
            return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 