package com.nurtivillage.java.nutrivillageApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurtivillage.java.nutrivillageApplication.dao.RoleRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.UserProfileRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.UserRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Location;
import com.nurtivillage.java.nutrivillageApplication.model.Role;
import com.nurtivillage.java.nutrivillageApplication.model.User;
import com.nurtivillage.java.nutrivillageApplication.model.UserProfile;
import com.nurtivillage.java.nutrivillageApplication.validation.Name;

@SpringBootApplication
public class NutrivillageApplication {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	

	
	@Autowired
	Name name;
	
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(NutrivillageApplication.class, args);
	}
	

	
	 @Bean
	 public WebMvcConfigurer corsConfigurer() 
	    {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**").allowedOrigins("*")
					.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
	            }
	        };
	    }

}
