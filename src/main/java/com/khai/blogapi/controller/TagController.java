package com.khai.blogapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khai.blogapi.payload.TagResponse;
import com.khai.blogapi.service.TagService;

@RestController
@RequestMapping("api/v1/tags")
public class TagController {

	@Autowired
	TagService tagService;
	
	@GetMapping
	public ResponseEntity<List<TagResponse>> getAllTags(){
		List<TagResponse> tagResponses = tagService.getAllTags();
		return new ResponseEntity<>(tagResponses,HttpStatus.OK);
	}
	
	@GetMapping("/{tag_id}")
	public ResponseEntity<TagResponse> getTagById(
			@PathVariable("tag_id") Long tagId){
		TagResponse tagResponses = tagService.getTagById(tagId);
		return new ResponseEntity<>(tagResponses,HttpStatus.OK);
	}
}
