package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.dao.OrderRepository;
import com.nurtivillage.java.nutrivillageApplication.model.UserOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    public OrderRepository orderRepository;

    public List<UserOrder> getAllOrder(){
        List<UserOrder> userOrder = orderRepository.findAll();
        return userOrder;
    }

    public UserOrder createOrder(UserOrder order){
        UserOrder orderCreate = orderRepository.save(order);
        return orderCreate;
    }

}
