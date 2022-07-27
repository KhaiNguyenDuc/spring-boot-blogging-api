package com.khai.blogapi.service;

import java.util.List;

import com.khai.blogapi.payload.CategoryResponse;

public interface CategoryService {

	List<CategoryResponse> getAllCategories();

	CategoryResponse getCategoryById(Long categoryId);

}
