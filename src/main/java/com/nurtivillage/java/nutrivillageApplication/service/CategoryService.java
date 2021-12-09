package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.nurtivillage.java.nutrivillageApplication.dao.CategoryRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Category;

@Service
public class CategoryService {
@Autowired
CategoryRepository categoryRepository;

public Category addCategory(Category category) {
try {
	Category c=categoryRepository.save(category);
	return c;
}
catch(Exception e) {
	throw e;
}
}
public List<Category> getCategories() {
try {
	return categoryRepository.findAll();
	
}
catch(Exception e) {
	throw e;
}
}

public Category getCategory(String name) {
	try {
		Category c=categoryRepository.findByName(name);
		return c;
	}
	catch(Exception e) {
		throw e;
	}
}
}
