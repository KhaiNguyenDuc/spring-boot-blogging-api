package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.payload.BlogResponse;

public interface BlogService {

	List<BlogResponse> getAllBlogs();

	BlogResponse getAllBlogs(Long blogId);

}
