package com.nurtivillage.java.nutrivillageApplication.controller;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.model.Review;
import com.nurtivillage.java.nutrivillageApplication.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @GetMapping("/list")
    public ResponseEntity<?> getAllReview(){
        try {
            List<Review> review = reviewService.getAllReview();
            return new ResponseEntity<List>(review,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
