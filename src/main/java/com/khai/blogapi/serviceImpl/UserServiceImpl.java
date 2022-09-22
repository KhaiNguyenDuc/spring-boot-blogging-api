package com.khai.blogapi.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.khai.blogapi.exception.AccessDeniedException;
import com.khai.blogapi.exception.ResourceExistException;
import com.khai.blogapi.exception.ResourceNotFoundException;
import com.khai.blogapi.exception.UserNotFoundException;
import com.khai.blogapi.model.Role;
import com.khai.blogapi.model.RoleName;
import com.khai.blogapi.model.User;
import com.khai.blogapi.payload.ApiResponse;
import com.khai.blogapi.payload.UserProfileResponse;
import com.khai.blogapi.payload.UserRequest;
import com.khai.blogapi.payload.UserResponse;
import com.khai.blogapi.repository.BlogRepository;
import com.khai.blogapi.repository.RoleRepository;
import com.khai.blogapi.repository.UserRepository;
import com.khai.blogapi.security.UserPrincipal;
import com.khai.blogapi.service.UserService;
import com.khai.blogapi.utils.AppConstant;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ModelMapper modelMapper;
	

	@Override
	public UserProfileResponse getCurrentUser(UserPrincipal userPrincipal) {
		
		String username = userPrincipal.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(
						AppConstant.USER_NOT_FOUND + username));
		
		Integer blogsCount = blogRepository.countByUser(user);
		
		UserProfileResponse userProfile = new UserProfileResponse();
		userProfile.setId(user.getId());
		userProfile.setUsername(username);
		userProfile.setFirstName(user.getFirstName());
		userProfile.setLastName(user.getLastName());
		userProfile.setEmail(user.getEmail());
		userProfile.setPhoneNumber(user.getPhoneNumber());
		userProfile.setImage(user.getImage());
		userProfile.setAddress(user.getAddress());
		userProfile.setBirthday(user.getBirthday());
		userProfile.setBlogsCount(blogsCount);
		userProfile.setEnabled(user.getEnabled());
		return userProfile;
		
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
	public UserProfileResponse getUserProfile(String username) {
		
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(
						AppConstant.USER_NOT_FOUND + username));
		
		Integer blogsCount = blogRepository.countByUser(user);
		
		UserProfileResponse userProfile = new UserProfileResponse();
		userProfile.setId(user.getId());
		userProfile.setUsername(username);
		userProfile.setFirstName(user.getFirstName());
		userProfile.setLastName(user.getLastName());
		userProfile.setEmail(user.getEmail());
		userProfile.setPhoneNumber(user.getPhoneNumber());
		userProfile.setImage(user.getImage());
		userProfile.setAddress(user.getAddress());
		userProfile.setBirthday(user.getBirthday());
		userProfile.setBlogsCount(blogsCount);
		userProfile.setEnabled(user.getEnabled());
			
		return userProfile;
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
			user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
			user.setEmail(userUpdate.getEmail());
			user.setEnabled(userUpdate.getEnabled());
			user.setImage(userUpdate.getImage());
			user.setAddress(userUpdate.getAddress());
			user.setPhoneNumber(userUpdate.getPhoneNumber());
			
			userRepository.save(user);
			return modelMapper.map(user, UserResponse.class);
		}
		throw new AccessDeniedException(AppConstant.USER_UPDATE_DENY);
		
		
	}

	@Override
	public ApiResponse giveAdmin(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.USER_NOT_FOUND + username));
		
		for(Role r : user.getRoles()) {
			if(r.getName().equals(RoleName.ADMIN)) {
				throw new ResourceExistException(AppConstant.ROLE_WITH_USER_EXIST);
			}
		}
		
		Role role = roleRepository.findByName(RoleName.ADMIN);
		if(Objects.isNull(role)) {
			throw new ResourceNotFoundException(AppConstant.ROLE_NOT_FOUND+ RoleName.ADMIN);
		}
		
		user.addRole(role);
		userRepository.save(user);
			
		
		return new ApiResponse(Boolean.TRUE,AppConstant.GIVE_ADMIN,HttpStatus.OK);
	}

	@Override
	public ApiResponse takeAdmin(String username) {
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException(
						AppConstant.USER_NOT_FOUND + username));
		
		List<Role> roles = user.getRoles().stream()
			.filter(role -> role.getName().equals(RoleName.ADMIN))
			.collect(Collectors.toList());
		
		if(roles.isEmpty()) {
			throw new ResourceNotFoundException(
					AppConstant.ROLE_NOT_FOUND_WITH_USER + RoleName.ADMIN);
		}
		
		user.removeRole(roles.get(0));
		
		userRepository.save(user);
		
		return new ApiResponse(Boolean.TRUE,AppConstant.TAKE_ADMIN,HttpStatus.OK);
	}

	@Override
	public UserProfileResponse createUser(UserRequest userRequest) {
		
		String username = userRequest.getUsername();
		if(userRepository.existsByUsername(username)) {
			throw new ResourceExistException(AppConstant.USER_EXIST);
		}
		
		Role role = roleRepository.findByName(RoleName.USER);
		if(Objects.isNull(role)) {
			throw new ResourceNotFoundException(AppConstant.ROLE_NOT_FOUND + RoleName.USER);
		}
		
		User user = modelMapper.map(userRequest, User.class);
		user.setRoles(Arrays.asList(role));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		return modelMapper.map(user, UserProfileResponse.class);
	}

}
