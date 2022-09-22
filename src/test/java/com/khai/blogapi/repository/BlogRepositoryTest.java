package com.khai.blogapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.model.Comment;
import com.khai.blogapi.model.Role;
import com.khai.blogapi.model.RoleName;
import com.khai.blogapi.model.Tag;
import com.khai.blogapi.model.User;
import com.khai.blogapi.payload.BlogRequest;
import com.khai.blogapi.payload.CategoryRequest;
import com.khai.blogapi.payload.CommentRequest;
import com.khai.blogapi.payload.TagRequest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@Rollback(false)
class BlogRepositoryTest {

	@Autowired
	BlogRepository blogRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	TagRepository tagRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	@Order(1)
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
		user.setPassword(passwordEncoder.encode("123"));
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
		userSub.setPassword(passwordEncoder.encode("123"));
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
		userSub1.setPassword(passwordEncoder.encode("123"));
		userSub1.setUsername("khang");

		Role roleSub1 = new Role();
		roleSub1.setName(RoleName.STUDENT);
		roleRepository.save(roleSub1);
		userSub1.setRoles(Arrays.asList(roleSub1));

		userRepository.save(userSub1);
	}

	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Order(2)
	void testCreateTag() {
		// create a tag
		TagRequest tagRequest = new TagRequest();
		tagRequest.setName("IT");
		tagRequest.setDescription("This is tag");
		tagRequest.setBlogs(null);

		Tag tag = modelMapper.map(tagRequest, Tag.class);
		tagRepository.save(tag);

		// 2nd tag
		TagRequest tagRequest2 = new TagRequest();
		tagRequest2.setName("MKT");
		tagRequest2.setDescription("This is MKT Tag");
		tagRequest2.setBlogs(null);

		Tag tag2 = modelMapper.map(tagRequest2, Tag.class);
		tagRepository.save(tag2);

		assertThat(tagRequest.equals(tag));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Order(3)
	void testCreateCategory() {

		User user = userRepository.findById(1L).get();
		
		// create category request
		CategoryRequest categoryRequest = new CategoryRequest();

		categoryRequest.setName("Spring boot rest api");
		categoryRequest.setTitle("This is title for spring boot rest api");
		categoryRequest.setDescription("Description");
		// map categoryRequest with category model
		Category cate = modelMapper.map(categoryRequest, Category.class);
		cate.setUser(user);
		categoryRepository.save(cate);

		// test that cate equals cateRequest
		assertThat(cate.equals(categoryRequest));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Order(4)
	void testCreateBlog() {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		List<Tag> tags = tagRepository.findAll();
		
		User user = userRepository.findById(1L).get();
		BlogRequest blogRequest = new BlogRequest();

		blogRequest.setImage("Image link");
		blogRequest.setBody("Body");
		blogRequest.setPublished(true);
		blogRequest.setDescription("This is description");
		blogRequest.setTitle("This is title");
		blogRequest.setViews(1L);
		blogRequest.setCategoryId(1L);

		
		Blog blog = modelMapper.map(blogRequest, Blog.class);
		blog.setUser(user);
		blog.setTags(tags);
		blogRepository.save(blog);

		assertThat(blog.equals(blogRequest));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Order(5)
	void testCreateComment() {

		// get blog with id 1
		Blog blog = blogRepository.findById(1L).get();
		User user = userRepository.findById(1L).get();
		// create comment and add blog
		CommentRequest commentRequest = new CommentRequest();
		
		commentRequest.setTitle("This is title");
		commentRequest.setBody("This is body");

		Comment comment = modelMapper.map(commentRequest, Comment.class);
		comment.setBlog(blog);
		comment.setUser(user);
		
		commentRepository.save(comment);

		// get blog with id 1

		// create comment and add blog
		CommentRequest commentRequest2 = new CommentRequest();
		
		commentRequest2.setTitle("This is title 2");
		commentRequest2.setBody("This is body 2");


		Comment comment2 = modelMapper.map(commentRequest2, Comment.class);
		comment2.setBlog(blog);
		comment2.setUser(user);
		commentRepository.save(comment2);

		assertThat(comment.equals(commentRequest));

	}

}
