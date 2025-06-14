package com.jaemin_web.demo.service;

import com.jaemin_web.demo.domain.Comment;
import com.jaemin_web.demo.domain.Post;
import com.jaemin_web.demo.domain.User;
import com.jaemin_web.demo.repository.CommentRepository;
import com.jaemin_web.demo.repository.PostRepository;
import com.jaemin_web.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Long createComment(Long postId, String content, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(user);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    public void updateComment(Long commentId, String content, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new IllegalStateException("댓글을 수정할 권한이 없습니다.");
        }

        comment.update(content);
    }

    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new IllegalStateException("댓글을 삭제할 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
} 