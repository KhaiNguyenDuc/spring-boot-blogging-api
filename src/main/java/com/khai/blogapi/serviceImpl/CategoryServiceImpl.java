package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khai.blogapi.model.Category;
import com.khai.blogapi.payload.CategoryResponse;
import com.khai.blogapi.repository.CategoryRepository;
import com.khai.blogapi.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<CategoryResponse> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryResponse> categoryResponses = 
				Arrays.asList(modelMapper.map(categories, CategoryResponse[].class));
		return categoryResponses;
	}
}
