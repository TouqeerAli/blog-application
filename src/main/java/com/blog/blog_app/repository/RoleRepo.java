package com.blog.blog_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.blog.blog_app.model.Role;

public interface RoleRepo extends CrudRepository<Role, Integer>{

}
