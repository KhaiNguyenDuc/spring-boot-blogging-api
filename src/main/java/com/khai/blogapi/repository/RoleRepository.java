package com.khai.blogapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khai.blogapi.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
