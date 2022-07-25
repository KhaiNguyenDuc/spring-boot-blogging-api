package com.khai.blogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

}
