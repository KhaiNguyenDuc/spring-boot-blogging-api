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
import com.khai.blogapi.payload.UserResponse;
import com.khai.blogapi.security.CurrentUser;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.UserService;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		UserResponse userResponse = userService.getCurrentUser(userPrincipal);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@GetMapping("/unique-username")
	public ResponseEntity<Boolean> checkUsernameUnique(
			@RequestParam("username") String username){
		
		Boolean unique = userService.checkUsernameUnique(username);
		
		return new ResponseEntity<>(unique,HttpStatus.OK);
	}
	
	@GetMapping("/unique-email")
	public ResponseEntity<Boolean> checkEmailUnique(
			@RequestParam("email") String email){
		
		Boolean unique = userService.checkEmailUnique(email);
		return new ResponseEntity<>(unique,HttpStatus.OK);
	}
	
	@GetMapping("/{username}/profiles")
	public ResponseEntity<UserResponse> getUserProfile(
			@PathVariable("username") String username){
		UserResponse userResponse = userService.getUserProfile(username);
		return new ResponseEntity<>(userResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteUserByUsername(
			@PathVariable("username") String username){
		ApiResponse response = userService.deleteUserByUsername(username);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PutMapping("/{username}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<UserResponse> updateUserByUsername(
			@PathVariable("username") String username,
			@RequestBody User userUpdate,
			@CurrentUser UserPrincipal currentUser){
		UserResponse userResponse = userService.updateUserByUsername(
				username,userUpdate,currentUser);
		return new ResponseEntity<>(userResponse,HttpStatus.OK);
	}
	
	
	
	

}
