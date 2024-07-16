package com.blog.blog_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blog_app.payload.ApiResponse;
import com.blog.blog_app.payload.UserDTO;
import com.blog.blog_app.service.UserService;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		UserDTO  dto =  this.userService.createUser(userDTO);
		return new ResponseEntity<>(dto,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") Integer userId){
		UserDTO dto = this.userService.updateUser(userDTO, userId);
		return ResponseEntity.ok(dto);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userid){
		this.userService.deleteUser(userid);
		return new ResponseEntity<>(new ApiResponse("User delete successfuly", true,HttpStatus.OK), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		
		return ResponseEntity.ok(this.userService.getAllUsers());	
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO>  getUser(@PathVariable("userId") Integer userId){
	UserDTO dto=	this.userService.getUserById(userId);
	return ResponseEntity.ok(dto);
	}
}
