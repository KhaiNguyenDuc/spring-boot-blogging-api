package com.khai.blogapi.service;

import com.khai.blogapi.security.UserPrincipal;

public interface CustomUserService {

	public UserPrincipal loadUserByUserId(Long userId);
}
