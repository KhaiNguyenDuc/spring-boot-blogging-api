package com.khai.blogapi.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.khai.blogapi.model.Role;
import com.khai.blogapi.model.RoleName;
import com.khai.blogapi.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Test
	void testCreateUser() {
		User user = new User();
		user.setAddress("address");
		user.setBirthday(null);
		user.setEmail("email");
		user.setComments(null);
		user.setBlogs(null);
		user.setCategories(null);
		user.setEnabled(true);
		user.setFirstName("First name");
		user.setLastName("Last name");
		user.setImage("Image");
		user.setPhoneNumber("Phone number");
		user.setPassword("123");
		user.setUsername("khai");
		
		Role role = new Role();
		role.setId(1L);
		role.setName(RoleName.ADMIN);
		roleRepository.save(role);
		user.setRoles(Arrays.asList(role));
		
		userRepository.save(user);
	}

}
