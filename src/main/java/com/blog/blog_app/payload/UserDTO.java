package com.blog.blog_app.payload;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserDTO {

	private Integer id;
	
	@NotEmpty
	@Size(min = 4, message = "username must be at least of 4 characters.")
	private String name;
	
	@NotEmpty
	@Email(message = "Email is not valid.")
	private String email;
	
	@NotEmpty
	@Size(min = 4,max = 10,message = "Password must be minimum 4 and max 10 characters.")
	private String password;
	
	@NotEmpty
	private String about;
	
	Set<RoleDTO> roles = new HashSet<>();

}
