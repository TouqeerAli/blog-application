package com.blog.blog_app.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.blog_app.config.AppConstants;
import com.blog.blog_app.exception.ResourceNotFoundException;
import com.blog.blog_app.model.Role;
import com.blog.blog_app.model.User;
import com.blog.blog_app.payload.UserDTO;
import com.blog.blog_app.repository.RoleRepo;
import com.blog.blog_app.repository.UserRepo;
import com.blog.blog_app.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = mapFromDto(userDTO);
		User savedUser = userRepo.save(user);
		return mapToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", " Id", id));
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		User savedUser =  this.userRepo.save(user);
		
		return mapToDto(savedUser);
	}

	@Override
	public UserDTO getUserById(Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", " Id", id));
		return mapToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDTO> userDtos =  users.stream().map(user -> this.mapToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer id) {
		User user = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", " Id", id));
		this.userRepo.delete(user);
	}
	
	public User mapFromDto(UserDTO userDTO) {
		
		User user = modelMapper.map(userDTO, User.class);
		return user;
		
//		return User.builder()
//				.id(userDTO.getId())
//				.name(userDTO.getName())
//				.email(userDTO.getEmail())
//				.password(userDTO.getPassword())
//				.about(userDTO.getAbout())
//				.build();
	}
	
	public UserDTO mapToDto(User user) {
		return modelMapper.map(user, UserDTO.class);
//		return UserDTO.builder()
//				.id(user.getId())
//				.name(user.getName())
//				.email(user.getEmail())
//				.password(user.getPassword())
//				.about(user.getAbout())
//				.build();
	}

	@Override
	public UserDTO registerUser(UserDTO userDTO) {
		
		User user =  this.mapFromDto(userDTO);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		Role role =  roleRepo.findById(AppConstants.USER).get();
		user.getRoles().add(role);
		
		User newUser =  this.userRepo.save(user);
		return this.mapToDto(newUser);
	}

}
