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
    private Long orderNO;
    private int itemNO;

    public OrderRequest(){}

    public OrderRequest(List<Product> item,double amount,User user,Long orderNO,int itemNO){}

}
