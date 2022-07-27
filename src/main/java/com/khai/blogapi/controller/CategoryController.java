package com.khai.blogapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.model.Category;
import com.khai.blogapi.payload.CategoryResponse;
import com.khai.blogapi.service.CategoryService;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAllCategories(){
		List<CategoryResponse> categoryResponse = 
				categoryService.getAllCategories(); 
		return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
	}
	
	@GetMapping("/{category_id}")
	public ResponseEntity<CategoryResponse> getCategoryById(
			@PathVariable("category_id") Long categoryId){
		CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
		return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
	}
}
