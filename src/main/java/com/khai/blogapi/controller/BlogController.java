package com.khai.blogapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.payload.BlogResponse;
import com.khai.blogapi.service.BlogService;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

	@Autowired
	BlogService blogService;
	
	@GetMapping
	public ResponseEntity<List<BlogResponse>> getAllBlogs(){
		List<BlogResponse> blogResponses = blogService.getAllBlogs();
		return new ResponseEntity<>(blogResponses,HttpStatus.OK);
	}
	
	@GetMapping("/{blog_id}")
	public ResponseEntity<BlogResponse> getBlogById(
			@PathVariable("blog_id") Long blogId){
		BlogResponse blogResponse = blogService.getAllBlogs(blogId);
		return new ResponseEntity<>(blogResponse,HttpStatus.OK);
	}
}
