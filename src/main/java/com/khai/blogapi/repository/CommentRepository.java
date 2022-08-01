package com.khai.blogapi.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Comment;
import com.khai.blogapi.model.User;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Page<Comment> findByBlog(Blog blog, Pageable pageable);

	void deleteAllByBlog(Blog blog);

	Page<Comment> findByUser(User user, Pageable pageable);

}
