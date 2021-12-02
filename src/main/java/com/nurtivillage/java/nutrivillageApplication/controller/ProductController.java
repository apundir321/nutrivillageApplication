package com.nurtivillage.java.nutrivillageApplication.controller;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.dao.ProductRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository; 
    @GetMapping("/list")
    public ResponseEntity<?> getAllProduct(){
        try{
            List<Product> product = productRepository.findAll();
            return  new ResponseEntity<List>(product,HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<String>("sting",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
