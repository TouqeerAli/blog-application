package com.blog.blog_app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blog_app.exception.ResourceNotFoundException;
import com.blog.blog_app.model.Category;
import com.blog.blog_app.payload.CategoryDTO;
import com.blog.blog_app.repository.CategoryRepo;
import com.blog.blog_app.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = mapFromDto(categoryDTO);
		Category savedCategory =  categoryRepo.save(category);
		return mapToDto(savedCategory);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id) {
		Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", " Category Id", id));
		category.setTitle(categoryDTO.getTitle());
		category.setDescription(categoryDTO.getDescription());

		return mapToDto(categoryRepo.save(category));
	}

	@Override
	public CategoryDTO getCategoryById(Integer id) {
		Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", " Category Id", id));
		
		return mapToDto(category);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<CategoryDTO> categoryDTOs = categoryRepo.findAll().stream().map(category -> mapToDto(category)).collect(Collectors.toList());
		
		return categoryDTOs;
	}

	@Override
	public void deleteCategory(Integer id) {
		Category category = categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", " Category id", id));
		categoryRepo.delete(category);
	}
	
	private Category mapFromDto(CategoryDTO categoryDTO ) {
		return modelMapper.map(categoryDTO, Category.class);
	}
	
	private CategoryDTO mapToDto(Category category) {
		return modelMapper.map(category, CategoryDTO.class);
	}

}
