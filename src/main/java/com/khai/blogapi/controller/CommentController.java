package com.khai.blogapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.payload.CommentResponse;
import com.khai.blogapi.payload.TagResponse;
import com.khai.blogapi.service.CommentService;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@GetMapping
	public ResponseEntity<List<CommentResponse>> getAllComments(){
		List<CommentResponse> commentResponses = 
				commentService.getAllComments();
		return new ResponseEntity<>(commentResponses,HttpStatus.OK);
	}
	
	@GetMapping("/{comment_id}")
	public ResponseEntity<CommentResponse> getCommentById(
			@PathVariable("comment_id") Long commentId){
		CommentResponse commentResponses = commentService.getCommentById(commentId);
		return new ResponseEntity<>(commentResponses,HttpStatus.OK);
	}
}
