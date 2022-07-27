package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Tag;
import com.khai.blogapi.payload.TagResponse;
import com.khai.blogapi.repository.BlogRepository;
import com.khai.blogapi.repository.TagRepository;
import com.khai.blogapi.service.TagService;
import com.khai.blogapi.utils.AppConstant;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	BlogRepository blogRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<TagResponse> getAllTags() {
		List<Tag> tags = tagRepository.findAll();
		return Arrays.asList(modelMapper.map(tags, TagResponse[].class));
	}

	@Override
	public TagResponse getTagById(Long tagId) {
		Tag tag = tagRepository.findById(tagId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.TAG_NOT_FOUND + tagId));
		return modelMapper.map(tag, TagResponse.class);
	}

	@Override
	public List<TagResponse> getTagsByBlog(Long blogId) {
		Blog blog = blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.BLOG_NOT_FOUND + blogId));
		List<Tag> tags = tagRepository.findByBlogs(blog);
		return Arrays.asList(modelMapper.map(tags, TagResponse[].class));
	}
}
