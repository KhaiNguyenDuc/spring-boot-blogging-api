package com.khai.blogapi.service;

import com.khai.blogapi.payload.CategoryResponse;
import com.khai.blogapi.payload.PageResponse;

public interface CategoryService {

	PageResponse<CategoryResponse> getAllCategories(Integer page, Integer size);

	CategoryResponse getCategoryById(Long categoryId);

}
