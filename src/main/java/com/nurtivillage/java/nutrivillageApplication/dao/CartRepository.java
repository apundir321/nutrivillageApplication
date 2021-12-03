package com.nurtivillage.java.nutrivillageApplication.dao;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.model.Cart;
import com.nurtivillage.java.nutrivillageApplication.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long>{
    
    List<Cart> findByUserId(Long id);
}
