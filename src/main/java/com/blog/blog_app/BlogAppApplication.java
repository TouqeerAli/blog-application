package com.blog.blog_app;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blog.blog_app.config.AppConstants;
import com.blog.blog_app.model.Role;
import com.blog.blog_app.repository.RoleRepo;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner{

	@Autowired
	RoleRepo roleRepo;
	
	public static void main(String[] args){
		SpringApplication.run(BlogAppApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		Role roleAdmin =  new Role();
		roleAdmin.setId(AppConstants.ADMIN);
		roleAdmin.setName("ROLE_ADMIN");
		
		Role roleUser =  new Role();
		roleUser.setId(AppConstants.USER);
		roleUser.setName("ROLE_USER");
		
		Role roleModerator =  new Role();
		roleModerator.setId(AppConstants.MODERATOR);
		roleModerator.setName("ROLE_MODERATOR");
		
		List<Role> roles = List.of(roleAdmin,roleUser,roleModerator);
		roleRepo.saveAll(roles);
		
		
	}

}
