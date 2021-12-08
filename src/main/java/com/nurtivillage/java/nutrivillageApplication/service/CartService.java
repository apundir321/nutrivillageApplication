package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.List;
import java.util.Optional;

import com.nurtivillage.java.nutrivillageApplication.dao.CartRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.UserRepository;
import com.nurtivillage.java.nutrivillageApplication.dto.CartResponseDto;
import com.nurtivillage.java.nutrivillageApplication.model.Cart;
import com.nurtivillage.java.nutrivillageApplication.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CartResponseDto>getCartItem(Long id){
        List<CartResponseDto> cartItem = cartRepository.findByUserId(id);
        return cartItem;
    }

    public Cart cartItemById(Long id){
        Optional<Cart> cartItem = cartRepository.findById(id);
        return cartItem.get();
    }

    public Cart insertCart(Cart cart){
        Cart cartItem = cartRepository.save(cart);
        return cartItem;
    }

    public String DeleteCartItem(Long id){
        cartRepository.deleteById(id);
        return "delete item form cart";
    }

    @Transactional
    public String cartClear(Long id){
        // Optional<User> user = userRepository.findById(id);
        cartRepository.deleteByUserId(id);
        return "hello";
    }

}
