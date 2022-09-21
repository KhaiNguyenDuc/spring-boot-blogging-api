package com.khai.blogapi.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.UserNotFoundException;
import com.khai.blogapi.model.User;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.CustomUserService;
import com.khai.blogapi.utils.AppConstant;

@Service
public class CustomUserServiceImpl implements UserDetailsService, CustomUserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(
						AppConstant.USER_NOT_FOUND + username));
		return UserPrincipal.create(user);

	}

	@Override
	public UserPrincipal loadUserByUserId(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(
						AppConstant.USER_NOT_FOUND + "id" + userId));
		return UserPrincipal.create(user);
	}

	

}
