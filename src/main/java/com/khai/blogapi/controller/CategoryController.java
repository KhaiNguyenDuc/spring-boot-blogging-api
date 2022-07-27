package com.khai.blogapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.payload.BlogResponse;
import com.khai.blogapi.payload.CategoryResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.CategoryService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	BlogService blogService;
	
	@GetMapping
	public ResponseEntity<PageResponse<CategoryResponse>> getAllCategories(
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size
			){
		PageResponse<CategoryResponse> categoryResponse = 
				categoryService.getAllCategories(page,size); 
		return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
	}
	
	@GetMapping("/{category_id}")
	public ResponseEntity<CategoryResponse> getCategoryById(
			@PathVariable("category_id") Long categoryId){
		CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
		return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
	}
	
	@GetMapping("/{category_id}/blogs")
	public ResponseEntity<PageResponse<BlogResponse>> getBlogsByCategory(
			@PathVariable("category_id") Long categoryId,
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size){
		PageResponse<BlogResponse> blogResponses = 
				blogService.getBlogsByCategory(categoryId,page,size);
		return new ResponseEntity<>(blogResponses,HttpStatus.OK);
	}
	
}
