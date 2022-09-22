package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.AccessDeniedException;
import com.khai.blogapi.exception.ResourceExistException;
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.RoleName;
import com.khai.blogapi.model.Tag;
import com.khai.blogapi.model.User;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.payload.TagRequest;
import com.khai.blogapi.payload.TagResponse;
import com.khai.blogapi.repository.BlogRepository;
import com.khai.blogapi.repository.TagRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
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
	UserRepository userRepository;

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

	@Override
	public ApiResponse deleteTagById(Long tagId) {
		Tag tag = tagRepository.findById(tagId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.TAG_NOT_FOUND + tagId));

		tagRepository.delete(tag);
		return new ApiResponse(Boolean.TRUE,AppConstant.TAG_DELETE_MESSAGE,HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteAllTag() {
		tagRepository.deleteAll();
		return new ApiResponse(Boolean.TRUE,AppConstant.TAG_DELETE_MESSAGE,HttpStatus.OK);
	}

	@Override
	public ApiResponse removeTagsByBlog(Long blogId, UserPrincipal userPrincipal) {
		Blog blog = blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.BLOG_NOT_FOUND + blogId));
		User user = userRepository.findByBlogs(blog);
		if(user.getId().equals(userPrincipal.getId()) ||
				userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_"+RoleName.ADMIN))) {
			
			List<Tag> tags = tagRepository.findAllByBlogs(blog);
			for (Tag tag : tags) {
				tag.removeBlog(blog);
			}
			
			tagRepository.saveAll(tags);
			return new ApiResponse(Boolean.TRUE,AppConstant.TAG_DELETE_MESSAGE,HttpStatus.OK);
			
		}
		throw new AccessDeniedException(AppConstant.TAG_REMOVE_DENY);
			
		
		
	}

	@Override
	public TagResponse updateTagById(Long tagId, TagRequest tagRequest) {
		Tag tag = tagRepository.findById(tagId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.TAG_NOT_FOUND+ tagId));
		modelMapper.map(tagRequest, tag);
		tagRepository.save(tag);
		
		
		return modelMapper.map(tag, TagResponse.class);
	}
}
