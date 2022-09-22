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
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.exception.UserNotFoundException;
import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Comment;
import com.khai.blogapi.model.RoleName;
import com.khai.blogapi.model.User;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.CommentRequest;
import com.khai.blogapi.payload.CommentResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.repository.BlogRepository;
import com.khai.blogapi.repository.CommentRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
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
	UserRepository userRepository;

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
	public CommentResponse addComment(Long blogId, CommentRequest commentRequest, UserPrincipal userPrincipal) {
		
		Comment comment = modelMapper.map(commentRequest, Comment.class);

		Blog blog = blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.BLOG_NOT_FOUND + blogId));

		User user = modelMapper.map(userPrincipal, User.class);

		comment.setUser(user);
		comment.setBlog(blog);

		commentRepository.save(comment);
		CommentResponse commentResponse =
				modelMapper.map(comment, CommentResponse.class);
		
		return commentResponse;
	}

	@Override
	public ApiResponse deleteById(Long commentId,UserPrincipal userPrincipal) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.COMMENT_NOT_FOUND + commentId));
		User user = userRepository.findByComments(comment);
		
		if (user.getId().equals(userPrincipal.getId()) || userPrincipal.getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))) {
			
			commentRepository.delete(comment);
			return new ApiResponse(Boolean.TRUE, AppConstant.COMMENT_DELETE_MESSAGE, HttpStatus.OK);
			
		}
		
		throw new AccessDeniedException(AppConstant.COMMENT_DELETE_DENY);
		
	}

	@Override
	public ApiResponse deleteCommentsByBlog(Long blogId, UserPrincipal userPrincipal) {
		Blog blog = blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.COMMENT_DELETE_MESSAGE));

		User user = userRepository.findByBlogs(blog);

		if (user.getId().equals(userPrincipal.getId())
				|| userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN))) {

			commentRepository.deleteAllByBlog(blog);

			return new ApiResponse(Boolean.TRUE, AppConstant.COMMENT_DELETE_MESSAGE, HttpStatus.OK);

		}
		throw new AccessDeniedException(AppConstant.COMMENT_DELETE_DENY);

	}

	@Override
	public CommentResponse updateCommentById(Long commentId, CommentRequest commentRequest, UserPrincipal userPrincipal) {
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.COMMENT_NOT_FOUND + commentId));
		User user = userRepository.findByComments(comment);

		if (user.getId().equals(userPrincipal.getId())
				|| userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN))) {

			modelMapper.map(commentRequest, comment);

			Comment commentSaved = commentRepository.save(comment);
			
			
			
			CommentResponse commentResponse = 
					modelMapper.map(commentSaved, CommentResponse.class);
		
			
			return commentResponse;
		}
		
		throw new AccessDeniedException(AppConstant.COMMENT_UPDATE_DENY);
	}

	@Override
	public PageResponse<CommentResponse> getCommentsByUsername(String username, Integer page, Integer size) {

		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(AppConstant.USER_NOT_FOUND + username));
		Page<Comment> comments = commentRepository.findByUser(user, pageable);
		List<CommentResponse> commentResponses = Arrays
				.asList(modelMapper.map(comments.getContent(), CommentResponse[].class));

		PageResponse<CommentResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(commentResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(comments.getTotalElements());
		pageResponse.setTotalPages(comments.getTotalPages());
		pageResponse.setLast(comments.isLast());

		return pageResponse;
	}


}
