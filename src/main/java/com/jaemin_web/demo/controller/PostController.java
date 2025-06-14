package com.jaemin_web.demo.controller;

import com.jaemin_web.demo.controller.dto.PostCreateRequestDto;
import com.jaemin_web.demo.controller.dto.PostResponseDto;
import com.jaemin_web.demo.controller.dto.PostUpdateRequestDto;
import com.jaemin_web.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Long> createPost(@Valid @RequestPart("request") PostCreateRequestDto requestDto,
                                           @RequestPart(value = "image", required = false) MultipartFile image,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        Long postId = postService.createPost(requestDto, image, userDetails.getUsername());
        return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Long> updatePost(@PathVariable("id") Long id,
                                           @Valid @RequestPart("request") PostUpdateRequestDto requestDto,
                                           @RequestPart(value = "image", required = false) MultipartFile image,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        Long updatedPostId = postService.updatePost(id, requestDto, image, userDetails.getUsername());
        return ResponseEntity.ok(updatedPostId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("id") Long id) {
        PostResponseDto postResponseDto = postService.getPostById(id);
        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePost(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
} 