package com.nurtivillage.java.nutrivillageApplication.dao;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.Review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByProduct(Product product);
}
