package com.khai.blogapi.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Tag;

@Repository
@Transactional
public interface TagRepository extends JpaRepository<Tag, Long> {

	Page<Tag> findByBlogs(Blog blog, Pageable pageable);

	Optional<Tag> findByName(String name);

	boolean existsByName(String name);

	List<Tag> findAllByBlogs(Blog blog);

}
