package com.khai.blogapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.model.User;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.BlogResponse;
import com.khai.blogapi.payload.CategoryResponse;
import com.khai.blogapi.payload.CommentResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.UserProfileResponse;
import com.khai.blogapi.payload.UserResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.CategoryService;
import com.khai.blogapi.service.CommentService;
import com.khai.blogapi.service.UserService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	BlogService blogService;

	@Autowired
	CommentService commentService;

	@Autowired
	CategoryService categoryService;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserProfileResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		UserProfileResponse userProfile = userService.getCurrentUser(userPrincipal);
		return new ResponseEntity<>(userProfile, HttpStatus.OK);
	}

	@GetMapping("/unique-username")
	public ResponseEntity<Boolean> checkUsernameUnique(@RequestParam("username") String username) {

		Boolean unique = userService.checkUsernameUnique(username);

		return new ResponseEntity<>(unique, HttpStatus.OK);
	}

	@GetMapping("/unique-email")
	public ResponseEntity<Boolean> checkEmailUnique(@RequestParam("email") String email) {

		Boolean unique = userService.checkEmailUnique(email);
		return new ResponseEntity<>(unique, HttpStatus.OK);
	}

	@GetMapping("/{username}/profiles")
	public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable("username") String username) {
		UserProfileResponse userProfile = userService.getUserProfile(username);
		return new ResponseEntity<>(userProfile, HttpStatus.OK);
	}

	@GetMapping("/{username}/blogs")
	public ResponseEntity<PageResponse<BlogResponse>> getBlogsByUsername(@PathVariable("username") String username,
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<BlogResponse> blogResponses = blogService.getBlogsByUsername(username, page, size);
		return new ResponseEntity<>(blogResponses, HttpStatus.OK);
	}

	@GetMapping("/{username}/comments")
	public ResponseEntity<PageResponse<CommentResponse>> getCommentsByUsername(
			@PathVariable("username") String username,
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<CommentResponse> commenResponse = commentService.getCommentsByUsername(username, page, size);
		return new ResponseEntity<>(commenResponse, HttpStatus.OK);
	}

	@GetMapping("/{username}/categories")
	public ResponseEntity<PageResponse<CategoryResponse>> getCategoriesByUsername(
			@PathVariable("username") String username,
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size) {
		PageResponse<CategoryResponse> categoriesResponse = categoryService.getCategoriesByUsername(username, page,
				size);
		return new ResponseEntity<>(categoriesResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteUserByUsername(@PathVariable("username") String username) {
		ApiResponse response = userService.deleteUserByUsername(username);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{username}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<UserResponse> updateUserByUsername(@PathVariable("username") String username,
			@RequestBody User userUpdate, @CurrentUser UserPrincipal currentUser) {
		UserResponse userResponse = userService.updateUserByUsername(username, userUpdate, currentUser);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}


}
