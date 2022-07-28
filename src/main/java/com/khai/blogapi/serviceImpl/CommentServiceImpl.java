package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Comment;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.CommentRequest;
import com.khai.blogapi.payload.CommentResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.repository.BlogRepository;
import com.khai.blogapi.repository.CommentRepository;
import com.khai.blogapi.service.CommentService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	BlogRepository blogRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PageResponse<CommentResponse> getAllComments(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);
		Page<Comment> comments = commentRepository.findAll(pageable);
		List<CommentResponse> commentResponses = Arrays
				.asList(modelMapper.map(comments.getContent(), CommentResponse[].class));

		PageResponse<CommentResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(commentResponses);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(comments.getNumberOfElements());
		pageResponse.setTotalPages(comments.getTotalPages());
		pageResponse.setLast(comments.isLast());

		return pageResponse;
	}

	@Override
	public CommentResponse getCommentById(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.COMMENT_NOT_FOUND + commentId));
		return modelMapper.map(comment, CommentResponse.class);
	}

	@Override
	public PageResponse<CommentResponse> getCommentsByBlog(Long blogId, Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);
		Blog blog = blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.BLOG_NOT_FOUND + blogId));
		Page<Comment> comments = commentRepository.findByBlog(blog, pageable);

		List<CommentResponse> commentResponses = Arrays
				.asList(modelMapper.map(comments.getContent(), CommentResponse[].class));

		PageResponse<CommentResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(commentResponses);
		pageResponse.setSize(size);
		pageResponse.setPage(page);
		pageResponse.setTotalElements(comments.getNumberOfElements());
		pageResponse.setTotalPages(comments.getTotalPages());
		pageResponse.setLast(comments.isLast());

		return pageResponse;
	}

	@Override
	public CommentResponse addComment(CommentRequest commentRequest, Long blogId) {
		Comment comment = modelMapper.map(commentRequest, Comment.class);
		
		Blog blog = blogRepository.findById(blogId).orElseThrow(
				() -> new ResourceNotFoundException(AppConstant.BLOG_NOT_FOUND + blogId));
		
		comment.setBlog(blog);
		comment.setCreateDate(new Date());
		
		commentRepository.save(comment);
		
		return modelMapper.map(comment,CommentResponse.class);
	}

	@Override
	public ApiResponse deleteById(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.COMMENT_NOT_FOUND + commentId));
		commentRepository.delete(comment);
		return new ApiResponse(Boolean.TRUE,AppConstant.COMMENT_DELETE_MESSAGE,HttpStatus.OK);
	}
	
	@Override
	public ApiResponse deleteCommentsByBlog(Long blogId) {
		Blog blog = blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.COMMENT_DELETE_MESSAGE));
		commentRepository.deleteAllByBlog(blog);
		
		return new ApiResponse(Boolean.TRUE,AppConstant.COMMENT_DELETE_MESSAGE,HttpStatus.OK);
	}

	@Override
	public CommentResponse updateCommentById(Long commentId, CommentRequest commentRequest) {
		
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.COMMENT_NOT_FOUND+commentId));
		
		modelMapper.map(commentRequest,comment);

		commentRepository.save(comment);
		
		return modelMapper.map(comment,CommentResponse.class);
	}

}
