package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.dao.ReviewRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;


    public List<Review>getAllReview(){
    	try {
        return reviewRepository.findAll();
    }
    	catch(Exception e) {
    	throw e;	
    	}
    	}
  
    public Review addReview(Review review) {
    	try {
    		Review r=reviewRepository.save(review);
    return r;
    	}
    	catch(Exception e) {
    		throw e;
    	}
    }
    public List<Review>getReview(Product product){
        List<Review> review = reviewRepository.findByProduct(product);
        return review;
    }

   

    
}
