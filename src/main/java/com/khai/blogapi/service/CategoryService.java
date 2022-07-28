package com.khai.blogapi.service;

import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.CategoryRequest;
import com.khai.blogapi.payload.CategoryResponse;
import com.khai.blogapi.payload.PageResponse;

public interface CategoryService {

	PageResponse<CategoryResponse> getAllCategories(Integer page, Integer size);

	CategoryResponse getCategoryById(Long categoryId);

	CategoryResponse createCategory(CategoryRequest categoryRequest);

	ApiResponse deleteCategoryById(Long categoryId);

	ApiResponse deleteAll();

	CategoryResponse updateCategoryById(Long categoryId, CategoryRequest categoryRequest);

	

}
