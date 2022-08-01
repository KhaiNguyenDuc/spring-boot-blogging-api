package com.khai.blogapi.service;

import com.khai.blogapi.model.User;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.UserProfileResponse;
import com.khai.blogapi.payload.UserRequest;
import com.khai.blogapi.payload.UserResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface UserService {

	UserProfileResponse getCurrentUser(UserPrincipal userPrincipal);

	Boolean checkUsernameUnique(String username);

	Boolean checkEmailUnique(String email);

	UserProfileResponse getUserProfile(String username);

	ApiResponse deleteUserByUsername(String username);

	UserResponse updateUserByUsername(String username, User userUpdate, UserPrincipal currentUser);

	ApiResponse giveAdmin(String username);

	ApiResponse takeAdmin(String username);

	UserProfileResponse createUser(UserRequest userRequest);

}
