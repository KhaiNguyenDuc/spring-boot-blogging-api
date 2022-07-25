package com.khai.blogapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.payload.BlogResponse;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

	@GetMapping
	public ResponseEntity<List<BlogResponse>> getAllBlogs(){
		return null;
	}
}
