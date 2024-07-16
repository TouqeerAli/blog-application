package com.blog.blog_app.service;

import java.util.List;

import com.blog.blog_app.payload.UserDTO;

public interface UserService {

	UserDTO createUser(UserDTO userDTO);
	UserDTO updateUser(UserDTO userDTO, Integer id);
	UserDTO getUserById(Integer id);
	List<UserDTO> getAllUsers();
	void deleteUser(Integer id);
	UserDTO registerUser(UserDTO userDTO);
}
