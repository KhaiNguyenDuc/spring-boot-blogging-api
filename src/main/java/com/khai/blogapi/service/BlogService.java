package com.khai.blogapi.service;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.BlogRequest;
import com.khai.blogapi.payload.BlogResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.security.UserPrincipal;

public interface BlogService {

	PageResponse<BlogResponse> getAllBlogs(Integer page, Integer size);

	BlogResponse getBlogsById(Long blogId);

	PageResponse<BlogResponse> getBlogsByCategory(Long categoryId, Integer page, Integer size);

	PageResponse<BlogResponse> getBlogsByTag(Long tagId, Integer page, Integer size);

	BlogResponse addBlog(BlogRequest blogRequest, UserPrincipal userPrincipal);

	ApiResponse deleteBlogById(Long blogId, UserPrincipal userPrincipal);

	ApiResponse deleteBlogsByCategory(Long categoryId, UserPrincipal userPrincipal);

	BlogResponse updateBlogById(Long blogId, BlogRequest blogRequest, UserPrincipal userPrincipal);

	PageResponse<BlogResponse> getBlogsByUsername(String username, Integer page, Integer size);


}
