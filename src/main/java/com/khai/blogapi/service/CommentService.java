package com.khai.blogapi.service;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.CommentRequest;
import com.khai.blogapi.payload.CommentResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface CommentService {

	PageResponse<CommentResponse> getAllComments(Integer page, Integer size);

	CommentResponse getCommentById(Long commentId);

	PageResponse<CommentResponse> getCommentsByBlog(Long blogId, Integer page, Integer size);

	ApiResponse deleteById(Long commentId, UserPrincipal userPrincipal);

	ApiResponse deleteCommentsByBlog(Long blogId, UserPrincipal userPrincipal);

	CommentResponse updateCommentById(Long commentId, CommentRequest commentRequest, UserPrincipal userPrincipal);

	PageResponse<CommentResponse> getCommentsByUsername(String username, Integer page, Integer size);

	CommentResponse addComment(Long blogId, CommentRequest commentRequest, UserPrincipal userPrincipal);

}
