package com.blog.blog_app.controller;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.blog_app.config.AppConstants;
import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.PostDTO;
import com.blog.blog_app.payload.PostResponse;
import com.blog.blog_app.service.FileService;
import com.blog.blog_app.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	PostService postService;
	
	@Autowired
	FileService fileService;
	
	@Value("${project.image}")
	String path;

	
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@PostMapping("/user/{userId}/category/{categoryId}/post/")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId) {

		PostDTO createdPost = postService.createPost(postDTO, userId, categoryId);

		return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);

	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable("postId") Integer postId){
		PostDTO updatedPost = postService.updatePost(postDTO, postId);
		return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	@GetMapping("post/")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue =  AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			
			
			){
		PostResponse posts =  postService.getAllPosts(pageNumber,pageSize, sortBy,sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<PostResponse> getAllPostsByUser(@PathVariable("userId") Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
			){
		PostResponse posts = postService.getAllPostsByUser(userId,pageNumber,pageSize);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<PostResponse> getAllPostsByCategory(@PathVariable("categoryId") Integer categoryId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
			){
		PostResponse posts = postService.getAllPostsByCategory(categoryId,pageNumber,pageSize);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDTO> getPost(@PathVariable("postId") Integer postId){
		PostDTO post = postService.getPost(postId);
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId){
		postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully.",true,HttpStatus.OK),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keywords") String keywords){
		List<PostDTO> posts =  postService.searchPostByTitle(keywords);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}
	
	//image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable("postId") Integer postId){
		
		PostDTO post = postService.getPost(postId);
		
		String fileName =  fileService.uploadImage(path, image);
		
		post.setImage(fileName);
	    PostDTO updatePost = postService.updatePost(post, postId);
		
		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
	}
	
	//image download
	
	@GetMapping(value = "/post/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("fileName") String fileName,
			HttpServletResponse response
			) throws IOException {
		
		InputStream resources = fileService.getResources(path, fileName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resources, response.getOutputStream());
		
	}

}
