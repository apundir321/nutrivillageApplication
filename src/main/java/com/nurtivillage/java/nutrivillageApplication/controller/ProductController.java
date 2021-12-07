package com.nurtivillage.java.nutrivillageApplication.controller;

import java.util.List;
import java.util.Optional;

import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.service.ApiResponseService;
import com.nurtivillage.java.nutrivillageApplication.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    public ResponseEntity<ApiResponseService> getAllProduct(){
        try{
            List<Product> product = productService.getAllProduct();
            ApiResponseService res = new ApiResponseService("Product List",true,product);
            return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<ApiResponseService> ProductInfo(@PathVariable Long id){
        try{
            Optional<Product> product = productService.ProductInfo(id);
            ApiResponseService res = new ApiResponseService("product info",true,List.of(product.get()));
            return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponseService> insertProduct(@RequestBody Product product){
        try {
            Product data = productService.insertProduct(product);
            ApiResponseService res = new ApiResponseService("product create",true,List.of(data));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponseService> editProduct(@RequestBody Product product){
        try {
            Product data = productService.insertProduct(product);
            ApiResponseService res = new ApiResponseService("update product",true,List.of(data));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseService> deleteProduct(@PathVariable Long id){
        try {
            String msg = productService.DeleteProduct(id);
            ApiResponseService res = new ApiResponseService(msg,true,List.of(id));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    @GetMapping(value="/highlighter")
    public ResponseEntity<ApiResponseService> highlighterProduct(){
        try {
            // List<Product> data = productService.highlighterProduct();
            ApiResponseService res = new ApiResponseService("List of highlighter",true,List.of("data"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
   
}
