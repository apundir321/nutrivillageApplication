package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.dao.ReviewRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review>getAllReview(){
        List<Review> review = reviewRepository.findAll();
        return review;
    }
    
}
