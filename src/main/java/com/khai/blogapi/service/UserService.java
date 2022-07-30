package com.khai.blogapi.service;

import com.khai.blogapi.model.User;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.UserResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface UserService {

	UserResponse getCurrentUser(UserPrincipal userPrincipal);

	Boolean checkUsernameUnique(String username);

	Boolean checkEmailUnique(String email);

	UserResponse getUserProfile(String username);

	ApiResponse deleteUserByUsername(String username);

	UserResponse updateUserByUsername(String username, User userUpdate, UserPrincipal currentUser);

}
