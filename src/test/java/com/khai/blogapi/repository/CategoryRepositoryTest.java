package com.khai.blogapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.khai.blogapi.model.Category;
import com.khai.blogapi.payload.CategoryRequest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class CategoryRepositoryTest {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	void testCreateCategory() {

		// create category request
		CategoryRequest categoryRequest = new CategoryRequest();
	
		categoryRequest.setId(1L);
		categoryRequest.setName("Spring boot rest api");
		categoryRequest.setTitle("This is title for spring boot rest api");
		categoryRequest.setDescription("Description");
		categoryRequest.setBlogs(null);
		
		// map categoryRequest with category model
		Category cate = new Category();
		modelMapper.map(categoryRequest, cate);
		categoryRepository.save(cate);
		
		// test that cate equals cateRequest
		assertThat(cate.equals(categoryRequest));
	}

}
