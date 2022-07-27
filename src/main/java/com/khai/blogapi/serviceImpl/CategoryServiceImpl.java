package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.payload.CategoryResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.repository.CategoryRepository;
import com.khai.blogapi.service.CategoryService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PageResponse<CategoryResponse> getAllCategories(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		Page<Category> categories = categoryRepository.findAll(pageable);
		List<CategoryResponse> categoryResponses = Arrays
				.asList(modelMapper.map(categories.getContent(), CategoryResponse[].class));

		PageResponse<CategoryResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(categoryResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(categories.getNumberOfElements());
		pageResponse.setTotalPages(categories.getTotalPages());
		pageResponse.setLast(categories.isLast());

		return pageResponse;
	}

	@Override
	public CategoryResponse getCategoryById(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));

		return modelMapper.map(category, CategoryResponse.class);
	}
}
