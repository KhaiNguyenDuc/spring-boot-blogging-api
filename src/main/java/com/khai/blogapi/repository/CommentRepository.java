package com.khai.blogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
