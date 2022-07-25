package com.khai.blogapi.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khai.blogapi.repository.BlogRepository;
import com.khai.blogapi.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService{

	@Autowired
	BlogRepository blogRepository;
	
	
}
