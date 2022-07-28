package com.khai.blogapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.test.annotation.Rollback;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.model.Comment;
import com.khai.blogapi.model.Tag;
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
	ModelMapper modelMapper;

	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Order(1)
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
	@Order(2)
	void testCreateCategory() {

		// create category request
		CategoryRequest categoryRequest = new CategoryRequest();

		categoryRequest.setName("Spring boot rest api");
		categoryRequest.setTitle("This is title for spring boot rest api");
		categoryRequest.setDescription("Description");
		categoryRequest.setBlogs(null);

		// map categoryRequest with category model
		Category cate = modelMapper.map(categoryRequest, Category.class);
		categoryRepository.save(cate);

		// test that cate equals cateRequest
		assertThat(cate.equals(categoryRequest));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Order(3)
	void testCreateBlog() {

		List<Tag> tags = tagRepository.findAll();

		BlogRequest blogRequest = new BlogRequest();

		blogRequest.setImage("Image link");
		blogRequest.setBody("Body");
		blogRequest.setLastUpdate(null);
		blogRequest.setComments(null);
		blogRequest.setCreateDate(new Date());
		blogRequest.setPublished(true);
		blogRequest.setDescription("This is description");
		blogRequest.setTags(tags);
		blogRequest.setTitle("This is title");
		blogRequest.setViews(1L);
		blogRequest.setCategoryId(1L);

		Blog blog = modelMapper.map(blogRequest, Blog.class);

		blogRepository.save(blog);

		assertThat(blog.equals(blogRequest));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Order(4)
	void testCreateComment() {

		// get blog with id 1
		Blog blog = blogRepository.findById(1L).get();

		// create comment and add blog
		CommentRequest commentRequest = new CommentRequest();
		
		commentRequest.setTitle("This is title");
		commentRequest.setBody("This is body");
		

		Comment comment = modelMapper.map(commentRequest, Comment.class);
		comment.setLastUpdate(null);
		comment.setCreateDate(new Date());
		comment.setBlog(blog);
		commentRepository.save(comment);

		// get blog with id 1

		// create comment and add blog
		CommentRequest commentRequest2 = new CommentRequest();
		
		commentRequest2.setTitle("This is title 2");
		commentRequest2.setBody("This is body 2");
		

		Comment comment2 = modelMapper.map(commentRequest2, Comment.class);
		comment2.setLastUpdate(null);
		comment2.setCreateDate(new Date());
		comment2.setBlog(blog);
		commentRepository.save(comment2);

		assertThat(comment.equals(commentRequest));

	}

}
