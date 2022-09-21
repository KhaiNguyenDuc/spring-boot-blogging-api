package com.khai.blogapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.JwtResponse;
import com.khai.blogapi.payload.LoginRequest;
import com.khai.blogapi.payload.UserProfileResponse;
import com.khai.blogapi.payload.UserRequest;
import com.khai.blogapi.security.JwtTokenProvider;
import com.khai.blogapi.service.UserService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> signin(
			@RequestBody LoginRequest loginRequest){
		
		UsernamePasswordAuthenticationToken authReq
		 = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		Authentication auth = authManager.authenticate(authReq);
		String token = tokenProvider.generateToken(auth);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return new ResponseEntity<>(new JwtResponse(token,"jwt"),HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserProfileResponse> register(
			@RequestBody UserRequest userRequest
			){
		UserProfileResponse userProfile = userService.createUser(userRequest);
		return new ResponseEntity<>(userProfile,HttpStatus.CREATED);
	}
	
	@PutMapping("/{username}/admins-permission")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> giveAdmin(
			@PathVariable("username") String username) {
		ApiResponse response = userService.giveAdmin(username);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{username}/admins-denial")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> takeAdmin(
			@PathVariable("username") String username) {
		ApiResponse response = userService.takeAdmin(username);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
