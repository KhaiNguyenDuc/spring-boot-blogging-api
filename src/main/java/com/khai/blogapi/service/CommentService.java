package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.payload.CommentResponse;

public interface CommentService {

	List<CommentResponse> getAllComments();

	CommentResponse getCommentById(Long commentId);

}
