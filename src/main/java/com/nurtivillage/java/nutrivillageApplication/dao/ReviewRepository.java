package com.nurtivillage.java.nutrivillageApplication.dao;

import com.nurtivillage.java.nutrivillageApplication.model.Review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    
}
