package com.khai.blogapi.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khai.blogapi.exception.ResourceExistException;
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.model.Comment;
import com.khai.blogapi.model.Tag;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.BlogRequest;
import com.khai.blogapi.payload.BlogResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.repository.BlogRepository;
import com.khai.blogapi.repository.CategoryRepository;
import com.khai.blogapi.repository.TagRepository;
import com.khai.blogapi.service.BlogService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
@Transactional
public class BlogServiceImpl implements BlogService{

	@Autowired
	BlogRepository blogRepository;

	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public PageResponse<BlogResponse> getAllBlogs(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);
		Page<Blog> blogs = blogRepository.findAll(pageable);
		List<BlogResponse> blogResponses = Arrays.asList(modelMapper.map(
				blogs.getContent(), BlogResponse[].class));
		
		PageResponse<BlogResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(blogResponses);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(blogs.getNumberOfElements());
		pageResponse.setTotalPages(blogs.getTotalPages());
		pageResponse.setLast(blogs.isLast());
		
		return pageResponse;
	}

	@Override
	public BlogResponse getBlogsById(Long blogId) {
		Blog blog = blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.BLOG_NOT_FOUND + blogId));
		return modelMapper.map(blog, BlogResponse.class);
	}

	@Override
	public PageResponse<BlogResponse> getBlogsByCategory(Long categoryId,Integer page, Integer size) {
		
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.CATEGORY_NOT_FOUND + categoryId));
	
		Page<Blog> blogs = blogRepository.findByCategory(category, pageable);
		
		List<BlogResponse> blogResponses =  Arrays.asList(modelMapper.map(blogs.getContent(),BlogResponse[].class));
		PageResponse<BlogResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(blogResponses);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(blogs.getNumberOfElements());
		pageResponse.setTotalPages(blogs.getTotalPages());
		pageResponse.setLast(blogs.isLast());
		
		return pageResponse;
	}

	@Override
	public PageResponse<BlogResponse> getBlogsByTag(Long tagId, Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);	
		Tag tag = tagRepository.findById(tagId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.TAG_NOT_FOUND + tagId));
		
		Page<Blog> blogs = blogRepository.findByTags(tag, pageable);
		
		List<BlogResponse> blogResponses = Arrays.asList(modelMapper.map(blogs.getContent(),BlogResponse[].class));
		
		PageResponse<BlogResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(blogResponses);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(blogs.getNumberOfElements());
		pageResponse.setTotalPages(blogs.getTotalPages());
		pageResponse.setLast(blogs.isLast());
		
		return pageResponse;
	}

	@Override
	public BlogResponse addBlog(BlogRequest blogRequest) {
		
		// skip id so that modelmapping not map categoryId with blogId
		modelMapper.typeMap(BlogRequest.class,Blog.class)
				.addMappings(mapper -> mapper.skip(Blog::setId));
		
		Blog blog = modelMapper.map(blogRequest, Blog.class);
		
		
		if(blogRepository.findByTitle(blog.getTitle()).isPresent()){
			throw new ResourceExistException(AppConstant.BLOG_EXIST);
		}
		
		Long id = blog.getCategory().getId();
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + id ));
		
		List<Tag> tags = new ArrayList<>();
		
		for (Tag tag : blog.getTags()) {
			
			if(!tagRepository.existsByName(tag.getName())) {
				tags.add(tagRepository.save(tag));
			}else {
				tags.add(tagRepository.findByName(tag.getName()).get());
			}

		}
		
		for(Comment comment : blog.getComments()) {
			comment.setBlog(blog);
		}
		
		blog.setTags(tags);
		System.out.println(blog.getId());
		blogRepository.save(blog);
		
		
		
		return modelMapper.map(blog,BlogResponse.class);
	}

	@Override
	public ApiResponse deleteBlogById(Long blogId) {
		Blog blog = blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.BLOG_NOT_FOUND + blogId));
		
		blogRepository.delete(blog);
		return new ApiResponse(Boolean.TRUE,AppConstant.BLOG_DELETE_MESSAGE,HttpStatus.OK);
	}

	@Override
	public ApiResponse deleteBlogsByCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.CATEGORY_NOT_FOUND + categoryId));
		
		blogRepository.deleteAllByCategory(category);
		return new ApiResponse(Boolean.TRUE,AppConstant.BLOG_DELETE_MESSAGE,HttpStatus.OK);
	}

	
}
