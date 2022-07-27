package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Blog;
import com.khai.blogapi.payload.BlogResponse;
import com.khai.blogapi.repository.BlogRepository;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.utils.AppConstant;
@Service
public class BlogServiceImpl implements BlogService{

	@Autowired
	BlogRepository blogRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<BlogResponse> getAllBlogs() {
		List<Blog> blogs = blogRepository.findAll();
		List<BlogResponse> blogResponses = Arrays.asList(modelMapper.map(
				blogs, BlogResponse[].class));
		return blogResponses;
	}

	@Override
	public BlogResponse getAllBlogs(Long blogId) {
		Blog blog = blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.BLOG_NOT_FOUND + blogId));
		return modelMapper.map(blog, BlogResponse.class);
	}
	
	
}
