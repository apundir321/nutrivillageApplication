package com.nurtivillage.java.nutrivillageApplication.Request;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.model.Cart;
import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.ShippingAddress;
import com.nurtivillage.java.nutrivillageApplication.model.User;

import org.springframework.stereotype.Service;

@Service
public class OrderRequest {
    private double amount;
    private List<Cart> cartItem;
    private ShippingAddress shippingAddress;

    public OrderRequest(){}

    public OrderRequest(List<Cart> cartItem,double amount,ShippingAddress shippingAddress){
        this.amount = amount;
        this.cartItem = cartItem;
        this.shippingAddress = shippingAddress;
    }

    public double getAmount() {
        return amount;
    }
    public List<Cart> getCartItem() {
        return cartItem;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }
    
}
