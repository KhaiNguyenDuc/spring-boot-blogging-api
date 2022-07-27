package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.payload.CategoryResponse;
import com.khai.blogapi.repository.CategoryRepository;
import com.khai.blogapi.service.CategoryService;
import com.khai.blogapi.utils.AppConstant;

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

	@Override
	public CategoryResponse getCategoryById(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.CATEGORY_NOT_FOUND+categoryId));

		return modelMapper.map(category, CategoryResponse.class);
	}
}
