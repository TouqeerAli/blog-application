package com.blog.blog_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blog.blog_app.security.CustomUserDetailService;
import com.blog.blog_app.security.JwtAuthenticationEntryPoint;
import com.blog.blog_app.security.JwtAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
	
    
    //-------- Without JWT
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//		
//		httpSecurity.csrf(csrf -> csrf.disable())
//		.authorizeHttpRequests(((authorizeHttpRequests) ->
// 				authorizeHttpRequests
// 					.anyRequest()
// 					.authenticated()))
//		.httpBasic();
//		
//		httpSecurity.authenticationProvider(daoAuthenticationProvider());
//		return httpSecurity.build();
//		
//	}
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(((authorizeHttpRequests) ->
			authorizeHttpRequests
				.requestMatchers("/api/v1/auth/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN", "MODERATOR")
                .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/posts/**").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .requestMatchers(HttpMethod.POST, "/api/posts/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/api/posts/**").hasAnyRole("ADMIN", "MODERATOR", "USER")
                .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasAnyRole("ADMIN", "MODERATOR", "USER")
				.anyRequest()
				.authenticated()))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customUserDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	



}
