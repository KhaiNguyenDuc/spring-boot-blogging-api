package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.model.Comment;
import com.khai.blogapi.payload.CommentResponse;
import com.khai.blogapi.repository.CommentRepository;
import com.khai.blogapi.service.CommentService;
import com.khai.blogapi.utils.AppConstant;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<CommentResponse> getAllComments() {
		List<Comment> comments = commentRepository.findAll();
		return Arrays.asList(modelMapper.map(comments, CommentResponse[].class));
	}

	@Override
	public CommentResponse getCommentById(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.COMMENT_NOT_FOUND + commentId));
		return modelMapper.map(comment, CommentResponse.class);
	}

}
