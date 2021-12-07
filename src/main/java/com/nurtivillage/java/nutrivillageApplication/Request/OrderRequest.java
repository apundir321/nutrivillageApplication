package com.nurtivillage.java.nutrivillageApplication.Request;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.User;

import org.springframework.stereotype.Service;

@Service
public class OrderRequest {
    private List<Product> item;
    private double amount;
    private User user;
    private List<Long> quantity;

    public OrderRequest(){}

    public OrderRequest(List<Product> item,List<Long> quantity,double amount,User user){
        this.item = item;
        this.quantity = quantity;
        this.amount = amount;
        this.user = user;;
    }

    public double getAmount() {
        return amount;
    }
    public List<Product> getItem() {
        return item;
    }
    public List<Long> getQuantity() {
        return quantity;
    }
    public User getUser() {
        return user;
    }
}