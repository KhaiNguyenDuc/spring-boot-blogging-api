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
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.TagResponse;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.service.TagService;
import com.khai.blogapi.utils.AppConstant;

@RestController
@RequestMapping("api/v1/tags")
public class TagController {

	@Autowired
	TagService tagService;
	
	@Autowired
	BlogService blogService;
	
	@GetMapping
	public ResponseEntity<PageResponse<TagResponse>> getAllTags(
			@RequestParam(value = "page", required = false, defaultValue =  AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size){
		PageResponse<TagResponse> tagResponses = tagService.getAllTags(page,size);
		return new ResponseEntity<>(tagResponses,HttpStatus.OK);
	}
	
	@GetMapping("/{tag_id}")
	public ResponseEntity<TagResponse> getTagById(
			@PathVariable("tag_id") Long tagId){
		TagResponse tagResponses = tagService.getTagById(tagId);
		return new ResponseEntity<>(tagResponses,HttpStatus.OK);
	}
	
	@GetMapping("/{tag_id}/blogs")
	public ResponseEntity<PageResponse<BlogResponse>> getBlogsByTag(
			@PathVariable("tag_id") Long tagId,
			@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size){
		PageResponse<BlogResponse> blogResponses = blogService.getBlogsByTag(tagId, page, size);
		return new ResponseEntity<>(blogResponses,HttpStatus.OK);
	}
	
	
}
