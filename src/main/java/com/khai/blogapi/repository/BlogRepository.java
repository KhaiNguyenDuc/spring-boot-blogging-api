package com.khai.blogapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.model.Tag;
import com.khai.blogapi.model.User;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

	Page<Blog> findByCategory(Category category, Pageable pageable);

	Page<Blog> findByTags(Tag tag, Pageable pageable);

	Optional<Blog> findByTitle(String title);

	void deleteAllByCategory(Category category);

	Page<Blog> findByUser(User user, Pageable pageable);

	Integer countByUser(User user);

}