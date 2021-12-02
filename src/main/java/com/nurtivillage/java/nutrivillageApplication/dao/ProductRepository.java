package com.nurtivillage.java.nutrivillageApplication.dao;

import com.nurtivillage.java.nutrivillageApplication.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>{
}
