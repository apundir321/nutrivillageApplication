package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.dao.CartRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Cart;
import com.nurtivillage.java.nutrivillageApplication.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart>getCartItem(Long id){
        List<Cart> cartItem = cartRepository.findByUserId(id);
        return cartItem;
    }

    public Cart insertCart(Cart cart){
        Cart cartItem = cartRepository.save(cart);
        return cartItem;
    }

    public String DeleteCartItem(Long id){
        cartRepository.deleteById(id);
        return "delete item form cart";
    }

}
