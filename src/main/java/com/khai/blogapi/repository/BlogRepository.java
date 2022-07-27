package com.khai.blogapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.model.Tag;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

	List<Blog> findByCategory(Category category);

	List<Blog> findByTags(Tag tag);

}