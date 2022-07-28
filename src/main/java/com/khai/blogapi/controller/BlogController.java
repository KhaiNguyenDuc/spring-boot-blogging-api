package com.khai.blogapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.BlogRequest;
import com.khai.blogapi.payload.BlogResponse;
import com.khai.blogapi.payload.CommentRequest;
import com.khai.blogapi.payload.CommentResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.TagResponse;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.CommentService;
import com.khai.blogapi.service.TagService;
import com.khai.blogapi.utils.AppConstant;

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
	public ResponseEntity<PageResponse<BlogResponse>> getAllBlogs(
			@RequestParam(value = "page", required = false, defaultValue =  AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size){
		PageResponse<BlogResponse> blogResponses = blogService.getAllBlogs(page,size);
		return new ResponseEntity<>(blogResponses,HttpStatus.OK);
	}
	
	@GetMapping("/{blog_id}")
	public ResponseEntity<BlogResponse> getBlogById(
			@PathVariable("blog_id") Long blogId){
		BlogResponse blogResponse = blogService.getBlogsById(blogId);
		return new ResponseEntity<>(blogResponse,HttpStatus.OK);
	}
	
	@GetMapping("/{blog_id}/comments")
	public ResponseEntity<PageResponse<CommentResponse>> getCommentsByBlog(
			@PathVariable("blog_id") Long blogId,
			@RequestParam(value = "page", required = false, defaultValue =  AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size){
		PageResponse<CommentResponse> commentsResponses = commentService.getCommentsByBlog(blogId, page, size);
		return new ResponseEntity<>(commentsResponses,HttpStatus.OK);
	}
	
	@GetMapping("/{blog_id}/tags")
	public ResponseEntity<PageResponse<TagResponse>> getTagsByBlog(
			@PathVariable("blog_id") Long blogId,
			@RequestParam(value = "page",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER ) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size){
			
		PageResponse<TagResponse> tagResponses = tagService.getTagsByBlog(blogId,page,size);
		return new ResponseEntity<>(tagResponses,HttpStatus.OK);
	}
	
	@PostMapping("/{blog_id}/comments")
	public ResponseEntity<CommentResponse> addComment(
			@PathVariable("blog_id") Long blogId,
			@RequestBody CommentRequest commentRequest){
		CommentResponse commentResponses = 
				commentService.addComment(commentRequest,blogId);
		return new ResponseEntity<>(commentResponses,HttpStatus.CREATED);
	}
	
	@PostMapping
	public ResponseEntity<BlogResponse> addBlog(
			@RequestBody BlogRequest blogRequest){
		BlogResponse blogResponse = blogService.addBlog(blogRequest);
		return new ResponseEntity<>(blogResponse,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{blog_id}")
	public ResponseEntity<ApiResponse> deleteBlogById(
			@PathVariable("blog_id") Long blogId){
		ApiResponse response = blogService.deleteBlogById(blogId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/{blog_id}/comments")
	public ResponseEntity<ApiResponse> deleteCommentsByBlog(
			@PathVariable("blog_id") Long blogId){
		ApiResponse response = commentService.deleteCommentsByBlog(blogId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/{blog_id}/tags")
	public ResponseEntity<ApiResponse> removeTagsByBlog(
			@PathVariable("blog_id") Long blogId){
		ApiResponse response = tagService.removeTagsByBlog(blogId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
