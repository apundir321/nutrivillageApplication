package com.nurtivillage.java.nutrivillageApplication.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import com.nurtivillage.java.nutrivillageApplication.dto.ProductInsert;

import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.Review;
import com.nurtivillage.java.nutrivillageApplication.service.AWSS3Service;
import com.nurtivillage.java.nutrivillageApplication.service.ApiResponseService;
import com.nurtivillage.java.nutrivillageApplication.service.InventoryService;
import com.nurtivillage.java.nutrivillageApplication.service.ProductService;
import com.nurtivillage.java.nutrivillageApplication.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;


@RestController
@RequestMapping(path = "/product")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired 
    private ReviewService reviewService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private AWSS3Service awsService;
    @GetMapping("/list")
    public ResponseEntity<ApiResponseService> getAllProduct(){
        try{
            List<Product> product = productService.getAllProduct();
            ApiResponseService res = new ApiResponseService("Product List",true,product);
            return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<ApiResponseService> ProductInfo(@PathVariable Long id){
        try{
            Optional<Product> product = productService.ProductInfo(id);
            List<Review> reviews = reviewService.getReview(product.get());
            List<Inventory> inventory = inventoryService.getProductInventory(product.get());
            product.get().setReview(reviews);

            // product.get().setVariant(inventory);

            ApiResponseService res = new ApiResponseService("product info",true,Arrays.asList(product.get()));

            return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponseService> insertProduct(@Valid @RequestBody Product product){
        try {
            Product insetProduct = productService.insertProduct(product);
            ApiResponseService res = new ApiResponseService("product create",true,Arrays.asList(insetProduct));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponseService> editProduct(@RequestBody Product product){
        try {
            Product data = productService.insertProduct(product);
            ApiResponseService res = new ApiResponseService("update product",true,Arrays.asList(data));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseService> deleteProduct(@PathVariable Long id){
        try {
            String msg = productService.DeleteProduct(id);
            ApiResponseService res = new ApiResponseService(msg,true,Arrays.asList(id));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    @GetMapping(value="/highlighter")
    public ResponseEntity<ApiResponseService> highlighterProduct(){
        try {
            List<Product> data = productService.highlighterProduct();
            ApiResponseService res = new ApiResponseService("List of highlighter",true,Arrays.asList(data));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/list/{categoryId}")
    public ResponseEntity<ApiResponseService> categoryProductLIst(@PathVariable Integer categoryId){
        try {
            List<Product> data = productService.categoryProductLIst(categoryId);
            ApiResponseService res = new ApiResponseService("List of highlighter",true,Arrays.asList(data));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/uploadimage/{id}")
	public ResponseEntity<?> updateProfilePic(@RequestPart(value= "file",required = true) final MultipartFile multipartFile,@PathVariable Long id)
			throws Exception {
		Optional<Product> product = null;
		File file = null;
		try {
			product  = productService.ProductInfo(id);
			String res = awsService.uploadProductFile(multipartFile,product.get());
			// profile.setProfilePicName(multipartFile.getOriginalFilename());
			// userProfileService.updateUserProfilePic(profile);
    		return new ResponseEntity<String>(res,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
