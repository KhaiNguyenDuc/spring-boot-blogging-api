package com.khai.blogapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Page<Comment> findByBlog(Blog blog, Pageable pageable);

}
