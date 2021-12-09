package com.nurtivillage.java.nutrivillageApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nurtivillage.java.nutrivillageApplication.model.Category;
import com.nurtivillage.java.nutrivillageApplication.service.CategoryService;

@RestController

public class CategoryController {
@Autowired
CategoryService categoryService;
@RequestMapping("/AddCategory")
public ResponseEntity<?> addCategory(@RequestBody Category category) {
	try {
	Category c = categoryService.addCategory(category);
	 return new ResponseEntity<Category>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@RequestMapping("/GetCategories")
public ResponseEntity<?> getCategories() {
	try {
	List<Category> c = categoryService.getCategories();
	 return new ResponseEntity<>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/getCategory/{name}")
public ResponseEntity<?> getCategory(@PathVariable String name) {
	try {
	Category c = categoryService.getCategory(name);
	 return new ResponseEntity<>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
}
