package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.ResourceExistException;
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Tag;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.TagRequest;
import com.khai.blogapi.payload.TagResponse;
import com.khai.blogapi.repository.BlogRepository;
import com.khai.blogapi.repository.TagRepository;
import com.khai.blogapi.service.TagService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	BlogRepository blogRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PageResponse<TagResponse> getAllTags(Integer page, Integer size) {
		
		AppUtils.validatePageAndSize(page,size);
		Pageable pageable = PageRequest.of(page, size);
		Page<Tag> tags = tagRepository.findAll(pageable);
		List<TagResponse> tagResponses =
				Arrays.asList(modelMapper.map(tags.getContent(),TagResponse[].class));
		PageResponse<TagResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(tagResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(tags.getNumberOfElements());
		pageResponse.setTotalPages(tags.getTotalPages());
		pageResponse.setLast(tags.isLast());
		return pageResponse;
	}

	@Override
	public PageResponse<TagResponse> getTagsByBlog(Long blogId, Integer page, Integer size) {
		
		AppUtils.validatePageAndSize(page,size);
		
		
		Blog blog = blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.BLOG_NOT_FOUND + blogId));
		
		Pageable pageable = PageRequest.of(page, size);
		Page<Tag> tags = tagRepository.findByBlogs(blog, pageable);
		
		List<TagResponse> tagResponses = Arrays.asList(
				modelMapper.map(tags.getContent(), TagResponse[].class));
		
		PageResponse<TagResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(tagResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(tags.getNumberOfElements());
		pageResponse.setTotalPages(tags.getTotalPages());
		pageResponse.setLast(tags.isLast());
		return pageResponse;
	}
	
	@Override
	public TagResponse getTagById(Long tagId) {
		Tag tag = tagRepository.findById(tagId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.TAG_NOT_FOUND + tagId));
		return modelMapper.map(tag, TagResponse.class);
	}

	@Override
	public TagResponse addTag(TagRequest tagRequest) {
		Tag tag = modelMapper.map(tagRequest,Tag.class);
		if(tagRepository.findByName(tag.getName()).isPresent()) {
			throw new ResourceExistException(AppConstant.TAG_EXIST);
		}
		tagRepository.save(tag);
		
		return modelMapper.map(tag,TagResponse.class);
	}
}
