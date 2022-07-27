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
import com.khai.blogapi.payload.CommentResponse;
import com.khai.blogapi.payload.TagResponse;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.CommentService;
import com.khai.blogapi.service.TagService;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

	@Autowired
	BlogService blogService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	TagService tagService;
	
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
	
	@GetMapping("/{blog_id}/comments")
	public ResponseEntity<List<CommentResponse>> getCommentsByBlog(
			@PathVariable("blog_id") Long blogId){
		List<CommentResponse> commentsResponses = commentService.getCommentsByBlog(blogId);
		return new ResponseEntity<>(commentsResponses,HttpStatus.OK);
	}
	
	@GetMapping("/{blog_id}/tags")
	public ResponseEntity<List<TagResponse>> getTagsByBlog(
			@PathVariable("blog_id") Long blogId){
		List<TagResponse> tagResponses = tagService.getTagsByBlog(blogId);
		return new ResponseEntity<>(tagResponses,HttpStatus.OK);
	}
	
}
