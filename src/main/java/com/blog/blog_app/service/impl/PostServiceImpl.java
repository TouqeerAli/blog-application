package com.blog.blog_app.service.impl;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.blog_app.exception.ResourceNotFoundException;
import com.blog.blog_app.model.Category;
import com.blog.blog_app.model.Post;
import com.blog.blog_app.model.User;
import com.blog.blog_app.payload.CategoryDTO;
import com.blog.blog_app.payload.PostDTO;
import com.blog.blog_app.payload.PostResponse;
import com.blog.blog_app.payload.UserDTO;
import com.blog.blog_app.repository.CategoryRepo;
import com.blog.blog_app.repository.PostRepo;
import com.blog.blog_app.repository.UserRepo;
import com.blog.blog_app.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepo postRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	

	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryid) {
		
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", " Id", userId));
		Category category = categoryRepo.findById(categoryid).orElseThrow(()-> new ResourceNotFoundException("Category", " Id", categoryid));
		
		postDTO.setImage("default.png");
		postDTO.setDate(new Date());
		postDTO.setUser(modelMapper.map(user, UserDTO.class));
		postDTO.setCategory(modelMapper.map(category, CategoryDTO.class));
		
		Post savedPost = postRepo.save(mapFromDto(postDTO));
		return mapToDto(savedPost);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer id) {
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", " Id", id));
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImage(postDTO.getImage());
		
		Post updatedPost = postRepo.save(post);
		return mapToDto(updatedPost);
	}

	@Override
	public void deletePost(Integer id) {
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", " Id", id));
		postRepo.delete(post);
	}

	@Override
	public PostDTO getPost(Integer id) {
		Post post = postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", " Id", id));
		return mapToDto(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = ("asc".equalsIgnoreCase(sortDir))?(sort= Sort.by(sortBy).ascending()):(sort= Sort.by(sortBy).descending());
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pageablePosts = postRepo.findAll(p);
		List<Post> posts = pageablePosts.getContent();
		List<PostDTO> dtos = posts.stream().map(post-> mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(dtos);
		postResponse.setPageNumber(pageNumber);
		postResponse.setPageSize(pageSize);
		postResponse.setTotlaElements(pageablePosts.getTotalElements());
		postResponse.setTotalPages(pageablePosts.getTotalPages());
		postResponse.setLastPage(pageablePosts.isLast());
		return postResponse;
	}

	@Override
	public PostResponse getAllPostsByUser(Integer userId,Integer pageNumber, Integer pageSize) {
		Pageable p = PageRequest.of(pageNumber, pageSize);
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id", userId));
		Page<Post> posts = (Page<Post>) this.postRepo.findByUser(user,p);
		List<PostDTO> postDtos =  posts.stream().map(post -> this.mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pageNumber);
		postResponse.setPageSize(pageSize);
		postResponse.setTotlaElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLastPage(posts.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getAllPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", " Id", categoryId));
		Pageable p = PageRequest.of(pageNumber, pageSize);
		Page<Post> posts = (Page<Post>) this.postRepo.findByCategory(category,p);
		List<PostDTO> postDtos =  posts.stream().map(post -> this.mapToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pageNumber);
		postResponse.setPageSize(pageSize);
		postResponse.setTotlaElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLastPage(posts.isLast());
		
		return postResponse;
	}
	
	
	@Override
	public List<PostDTO> searchPostByTitle(String keywords) {
		List<Post> posts = postRepo.findByTitleContaining(keywords);
		List<PostDTO> dtos =  posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return dtos;
	}
	
	private PostDTO mapToDto(Post post) {
		return modelMapper.map(post, PostDTO.class);
	}
	
	private Post mapFromDto(PostDTO postDTO) {
		return modelMapper.map(postDTO, Post.class);
	}

	

}
