package com.blog.blog_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.blog_app.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
  
	Optional<User> findByEmail(String email);
}
