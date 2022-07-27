package com.khai.blogapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.payload.CommentResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.service.CommentService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@GetMapping
	public ResponseEntity<PageResponse<CommentResponse>> getAllComments(
			@RequestParam(value = "page", required = false, defaultValue =  AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size){
		PageResponse<CommentResponse> commentResponses = 
				commentService.getAllComments(page,size);
		return new ResponseEntity<>(commentResponses,HttpStatus.OK);
	}
	
	@GetMapping("/{comment_id}")
	public ResponseEntity<CommentResponse> getCommentById(
			@PathVariable("comment_id") Long commentId){
		CommentResponse commentResponses = commentService.getCommentById(commentId);
		return new ResponseEntity<>(commentResponses,HttpStatus.OK);
	}
}
