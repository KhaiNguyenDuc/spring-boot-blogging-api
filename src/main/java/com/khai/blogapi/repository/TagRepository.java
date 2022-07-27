package com.khai.blogapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

	List<Tag> findByBlogs(Blog blog);

}
