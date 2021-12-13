package com.nurtivillage.java.nutrivillageApplication.dao;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.model.Category;
import com.nurtivillage.java.nutrivillageApplication.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product,Integer>{

    
    List<Product> findByDeletedAtIsNull();

    List<Product> findByStatusAndDeletedAtIsNull(int status);

    List<Product> findByCategoryIdAndDeletedAtIsNull(Integer categoryId);
}
