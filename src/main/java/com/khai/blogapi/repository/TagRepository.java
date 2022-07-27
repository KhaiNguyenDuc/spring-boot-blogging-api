package com.khai.blogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
