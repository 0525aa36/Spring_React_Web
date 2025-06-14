package com.jaemin_web.demo.repository;

import com.jaemin_web.demo.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface CommentRepository extends JpaRepository<Comment, Long> {
} 