package com.blog.blog_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.blog_app.payload.CategoryDTO;


public interface CategoryService {
	
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id);
	CategoryDTO getCategoryById(Integer id);
	List<CategoryDTO> getAllCategories();
	void deleteCategory(Integer id);

}
