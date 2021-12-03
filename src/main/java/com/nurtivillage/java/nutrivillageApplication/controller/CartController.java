package com.nurtivillage.java.nutrivillageApplication.controller;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.dao.CartRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.UserRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Cart;
import com.nurtivillage.java.nutrivillageApplication.model.User;
import com.nurtivillage.java.nutrivillageApplication.service.ApiResponseService;
import com.nurtivillage.java.nutrivillageApplication.service.CartService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
            private UserRepository userRepository;
    @GetMapping("/list/{id}")
    public ResponseEntity<ApiResponseService> getAllCartItem(@PathVariable Long id){
        try {
            
            // User user = userRepository.findById(id);
            List<Cart> cartItem = cartService.getCartItem(id);
            ApiResponseService res = new ApiResponseService("Cart item",true,cartItem);
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("data"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponseService> insertCart(@RequestBody Cart cart){
        try {
            Cart cartItem = cartService.insertCart(cart);
            ApiResponseService res = new ApiResponseService("Cart item insert",true,List.of(cart));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("data"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<ApiResponseService> editCart(@RequestBody Cart cart){
        try {
            Cart cartItem = cartService.insertCart(cart);
            ApiResponseService res = new ApiResponseService("Cart item insert",true,List.of(cart));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("data"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<ApiResponseService> removeFromCart(@PathVariable Long id){
        try {
            cartService.DeleteCartItem(id);
            ApiResponseService res = new ApiResponseService("Delete item from cart",true,List.of(id));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("data"));
            return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
