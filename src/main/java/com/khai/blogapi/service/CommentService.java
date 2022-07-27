package com.khai.blogapi.service;

import com.khai.blogapi.payload.CommentRequest;
import com.khai.blogapi.payload.CommentResponse;
import com.khai.blogapi.payload.PageResponse;

public interface CommentService {

	PageResponse<CommentResponse> getAllComments(Integer page, Integer size);

	CommentResponse getCommentById(Long commentId);

	PageResponse<CommentResponse> getCommentsByBlog(Long blogId, Integer page, Integer size);

	CommentResponse addComment(CommentRequest commentRequest, Long blogId);

}
