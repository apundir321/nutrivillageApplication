package com.nurtivillage.java.nutrivillageApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nurtivillage.java.nutrivillageApplication.model.Category;



public interface CategoryRepository extends JpaRepository<Category,Integer> {
Category findByCode(String code);
Category findByName(String name);
}
