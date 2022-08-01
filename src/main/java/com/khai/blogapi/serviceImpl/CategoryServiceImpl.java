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
import com.khai.blogapi.exception.UserNotFoundException;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.model.RoleName;
import com.khai.blogapi.model.User;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.CategoryRequest;
import com.khai.blogapi.payload.CategoryResponse;
import com.khai.blogapi.payload.PageResponse;
import com.khai.blogapi.repository.CategoryRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.CategoryService;
import com.khai.blogapi.utils.AppConstant;
import com.khai.blogapi.utils.AppUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PageResponse<CategoryResponse> getAllCategories(Integer page, Integer size) {
		AppUtils.validatePageAndSize(page, size);
		Pageable pageable = PageRequest.of(page, size);

		Page<Category> categories = categoryRepository.findAll(pageable);
		List<CategoryResponse> categoryResponses = Arrays
				.asList(modelMapper.map(categories.getContent(), CategoryResponse[].class));

		PageResponse<CategoryResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(categoryResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(categories.getNumberOfElements());
		pageResponse.setTotalPages(categories.getTotalPages());
		pageResponse.setLast(categories.isLast());

		return pageResponse;
	}

	@Override
	public CategoryResponse getCategoryById(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));

		return modelMapper.map(category, CategoryResponse.class);
	}

	@Override
	public CategoryResponse createCategory(CategoryRequest categoryRequest, UserPrincipal userPrincipal) {

		Category category = modelMapper.map(categoryRequest, Category.class);

		if (categoryRepository.findByName(category.getName()).isPresent()) {
			throw new ResourceExistException(AppConstant.CATEGORY_EXIST);
		}

		User user = modelMapper.map(userPrincipal, User.class);
		category.setUser(user);

		categoryRepository.save(category);

		return modelMapper.map(category, CategoryResponse.class);

	}

	@Override
	public ApiResponse deleteCategoryById(Long categoryId, UserPrincipal userPrincipal) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));

		User user = userRepository.findByCategories(category);
		if (user.getId().equals(userPrincipal.getId()) || userPrincipal.getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))) {
			categoryRepository.delete(category);
			return new ApiResponse(Boolean.TRUE, AppConstant.CATEGORY_DELETE_MESSAGE, HttpStatus.OK);
		}
		throw new AccessDeniedException(AppConstant.CATEGORY_DELETE_DENY);
	}

	@Override
	public ApiResponse deleteAll() {
		categoryRepository.deleteAll();
		return new ApiResponse(Boolean.TRUE, AppConstant.CATEGORY_DELETE_MESSAGE, HttpStatus.OK);
	}

	@Override
	public CategoryResponse updateCategoryById(Long categoryId, CategoryRequest categoryRequest,
			UserPrincipal userPrincipal) {

		if(categoryRepository.existsByName(categoryRequest.getName())) {
			throw new ResourceExistException(AppConstant.CATEGORY_EXIST);
		}
		
		modelMapper.typeMap(CategoryRequest.class, Category.class).addMappings(mapper -> mapper.skip(Category::setId));

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY_NOT_FOUND + categoryId));

		User user = userRepository.findByCategories(category);
		if (user.getId().equals(userPrincipal.getId()) || userPrincipal.getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_" + RoleName.ADMIN.toString()))) {

			modelMapper.map(categoryRequest, category);

			categoryRepository.save(category);

			return modelMapper.map(category, CategoryResponse.class);
		}

		throw new AccessDeniedException(AppConstant.CATEGORY_UPDATE_DENY);
	}

	@Override
	public PageResponse<CategoryResponse> getCategoriesByUsername(String username, Integer page, Integer size) {

		AppUtils.validatePageAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size);

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(AppConstant.USER_NOT_FOUND + username));
		Page<Category> categories = categoryRepository.findByUser(user, pageable);
		List<CategoryResponse> categoryResponses = Arrays
				.asList(modelMapper.map(categories.getContent(), CategoryResponse[].class));
		PageResponse<CategoryResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(categoryResponses);
		pageResponse.setPage(page);
		pageResponse.setSize(size);
		pageResponse.setTotalElements(categories.getTotalElements());
		pageResponse.setTotalPages(categories.getTotalPages());
		pageResponse.setLast(categories.isLast());

		return pageResponse;
	}

}
