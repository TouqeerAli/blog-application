package com.blog.blog_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.CategoryDTO;
import com.blog.blog_app.service.CategoryService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController()
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		 CategoryDTO category =  categoryService.createCategory(categoryDTO);
		 return new ResponseEntity<CategoryDTO>(category, HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable("categoryId") Integer id){
		CategoryDTO dto = categoryService.updateCategory(categoryDTO, id);
		return new ResponseEntity<CategoryDTO>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		
		return new ResponseEntity<List<CategoryDTO>>(categoryService.getAllCategories(), HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getSingleCategory(@PathVariable("categoryId") Integer id){
		
		return new ResponseEntity<CategoryDTO>(categoryService.getCategoryById(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer id){
		categoryService.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Successfully.", true, HttpStatus.OK), HttpStatus.OK);
	}

}
