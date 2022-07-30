package com.khai.blogapi.repository;

import java.util.Arrays;

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
		user.setEmail("email@gmail.com");
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
		role.setName(RoleName.ADMIN);
		roleRepository.save(role);
		user.setRoles(Arrays.asList(role));

		userRepository.save(user);

		// add user 2
		User userSub = new User();
		userSub.setAddress("address sub");
		userSub.setBirthday(null);
		userSub.setEmail("emailSub@gmail.com");
		userSub.setComments(null);
		userSub.setBlogs(null);
		userSub.setCategories(null);
		userSub.setEnabled(true);
		userSub.setFirstName("First name sub");
		userSub.setLastName("Last name sub");
		userSub.setImage("Image sub");
		userSub.setPhoneNumber("Phone number sub");
		userSub.setPassword("123");
		userSub.setUsername("kiet");

		Role roleSub = new Role();
		roleSub.setName(RoleName.USER);
		roleRepository.save(roleSub);
		userSub.setRoles(Arrays.asList(roleSub));

		userRepository.save(userSub);

		// add user 3
		User userSub1 = new User();
		userSub1.setAddress("address sub1");
		userSub1.setBirthday(null);
		userSub1.setEmail("emailSub1@gmail.com");
		userSub1.setComments(null);
		userSub1.setBlogs(null);
		userSub1.setCategories(null);
		userSub1.setEnabled(true);
		userSub1.setFirstName("First name sub1");
		userSub1.setLastName("Last name sub1");
		userSub1.setImage("Image sub1");
		userSub1.setPhoneNumber("Phone number sub1");
		userSub1.setPassword("123");
		userSub1.setUsername("khang");

		Role roleSub1 = new Role();
		roleSub1.setName(RoleName.STUDENT);
		roleRepository.save(roleSub1);
		userSub1.setRoles(Arrays.asList(roleSub1));

		userRepository.save(userSub1);
	}

}
