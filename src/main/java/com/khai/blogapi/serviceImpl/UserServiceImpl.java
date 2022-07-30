package com.khai.blogapi.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.UnauthorizedException;
import com.khai.blogapi.exception.UserNotFoundException;
import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.RoleName;
import com.khai.blogapi.model.User;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.BlogRequest;
import com.khai.blogapi.payload.UserResponse;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.UserService;
import com.khai.blogapi.utils.AppConstant;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public UserResponse getCurrentUser(UserPrincipal userPrincipal) {
		
		return modelMapper.map(userPrincipal,UserResponse.class);
		
	}

	@Override
	public Boolean checkUsernameUnique(String username) {
		return !userRepository.existsByUsername(username);
	}

	@Override
	public Boolean checkEmailUnique(String email) {
		return !userRepository.existsByEmail(email);
	}

	@Override
	public UserResponse getUserProfile(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(
						AppConstant.USER_NOT_FOUND + username));
		
		return modelMapper.map(user, UserResponse.class);
	}

	@Override
	public ApiResponse deleteUserByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(
						AppConstant.USER_NOT_FOUND + username));
		userRepository.delete(user);
		return new ApiResponse(Boolean.TRUE,AppConstant.USER_DELETE_MESSAGE,HttpStatus.OK);
	}

	@Override
	public UserResponse updateUserByUsername(String username, User userUpdate, UserPrincipal currentUser) {
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(
						AppConstant.USER_NOT_FOUND + username));
		if(user.getId().equals(currentUser.getId()) || 
				currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_"+RoleName.ADMIN.toString()))) {
			
			user.setUsername(userUpdate.getUsername());
			user.setBirthday(userUpdate.getBirthday());
			user.setFirstName(userUpdate.getFirstName());
			user.setLastName(userUpdate.getLastName());
			System.out.println(userUpdate.getPassword());
			user.setPassword(userUpdate.getPassword());
			user.setEmail(userUpdate.getEmail());
			user.setEnabled(userUpdate.getEnabled());
			user.setImage(userUpdate.getImage());
			user.setAddress(userUpdate.getAddress());
			user.setPhoneNumber(userUpdate.getPhoneNumber());
			
			userRepository.save(user);
			return modelMapper.map(user, UserResponse.class);
		}
		throw new UnauthorizedException(AppConstant.USER_UPDATE_DENY);
		
		
	}

}
