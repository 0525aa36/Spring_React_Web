package com.jaemin_web.demo.service;

import com.jaemin_web.demo.controller.dto.PostCreateRequestDto;
import com.jaemin_web.demo.controller.dto.PostResponseDto;
import com.jaemin_web.demo.controller.dto.PostUpdateRequestDto;
import com.jaemin_web.demo.domain.Post;
import com.jaemin_web.demo.domain.User;
import com.jaemin_web.demo.repository.PostRepository;
import com.jaemin_web.demo.repository.UserRepository;
import com.jaemin_web.demo.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public Long createPost(PostCreateRequestDto requestDto, MultipartFile image, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.storeFile(image);
            imageUrl = "/uploads/" + fileName;
        }

        Post post = new Post();
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        post.setAuthor(user);
        post.setImageUrl(imageUrl);

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional
    public Long updatePost(Long id, PostUpdateRequestDto requestDto, MultipartFile image, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new IllegalStateException("게시글을 수정할 권한이 없습니다.");
        }

        if (image != null && !image.isEmpty()) {
            if (post.getImageUrl() != null) {
                fileStorageService.deleteFile(post.getImageUrl().replace("/uploads/", ""));
            }
            String fileName = fileStorageService.storeFile(image);
            post.setImageUrl("/uploads/" + fileName);
        } else if (requestDto.isRemoveImage()) {
            if (post.getImageUrl() != null) {
                fileStorageService.deleteFile(post.getImageUrl().replace("/uploads/", ""));
                post.setImageUrl(null);
            }
        }

        post.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new IllegalStateException("게시글을 삭제할 권한이 없습니다.");
        }

        if (post.getImageUrl() != null) {
            fileStorageService.deleteFile(post.getImageUrl().replace("/uploads/", ""));
        }

        postRepository.delete(post);
    }
} 