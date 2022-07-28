package com.khai.blogapi.service;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.BlogRequest;
import com.khai.blogapi.payload.BlogResponse;
import com.khai.blogapi.payload.PageResponse;

public interface BlogService {

	PageResponse<BlogResponse> getAllBlogs(Integer page, Integer size);

	BlogResponse getBlogsById(Long blogId);

	PageResponse<BlogResponse> getBlogsByCategory(Long categoryId, Integer page, Integer size);

	PageResponse<BlogResponse> getBlogsByTag(Long tagId, Integer page, Integer size);

	BlogResponse addBlog(BlogRequest blogRequest);

	ApiResponse deleteBlogById(Long blogId);

	ApiResponse deleteBlogsByCategory(Long categoryId);


}
