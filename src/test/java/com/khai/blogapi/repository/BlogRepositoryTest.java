package com.khai.blogapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.khai.blogapi.model.Blog;
import com.khai.blogapi.model.Category;
import com.khai.blogapi.payload.BlogRequest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class BlogRepositoryTest {

	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	void testCreateBlog() {
		
		Category category = categoryRepository.findById(1L).get();
		
		BlogRequest blogRequest = new BlogRequest();
		blogRequest.setId(1L);
		blogRequest.setImage("Image link");
		blogRequest.setBody("Body");
		blogRequest.setLastUpdate(null);
		blogRequest.setComments(null);
		blogRequest.setCreateDate(null);
		blogRequest.setPublished(true);
		blogRequest.setDescription("This is description");
		blogRequest.setTags(null);
		blogRequest.setTitle(null);
		blogRequest.setViews(1L);
		blogRequest.setCategory(category);
		
		Blog blog = modelMapper.map(blogRequest, Blog.class);
		
		blogRepository.save(blog);
		
		assertThat(blog.equals(blogRequest));
	}

}
