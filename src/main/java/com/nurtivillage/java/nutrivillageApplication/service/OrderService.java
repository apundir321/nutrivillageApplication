package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nurtivillage.java.nutrivillageApplication.dao.OrderDetailsRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.OrderRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Cart;
import com.nurtivillage.java.nutrivillageApplication.model.OrderDetails;
import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.Status;
import com.nurtivillage.java.nutrivillageApplication.model.User;
import com.nurtivillage.java.nutrivillageApplication.model.UserOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    public OrderRepository orderRepository;

    @Autowired 
    public OrderDetailsRepository orderDetailsRepository; 

    @Autowired
    public OrderService orderService;

    @Autowired
    public CartService cartService;

    public List<UserOrder> getAllOrder(){
        List<UserOrder> userOrder = orderRepository.findAll();
        return userOrder;
    }

    public UserOrder createOrder(UserOrder order){
        UserOrder orderCreate = orderRepository.save(order);
        return orderCreate;
    }

    public List<OrderDetails> createOrderDetails(List<Cart> cartItems,UserOrder order){//(List<Product> product,UserOrder order,List<Long> quantity){
        int size = cartItems.size();
        List<OrderDetails> orderAllItem = new ArrayList<>();
        cartItems.forEach((var)->{
            Cart cartItem = cartService.cartItemById(var.getId());
            OrderDetails orderItem = new OrderDetails(cartItem.getProduct(),order,cartItem.getQuantity(),cartItem.getVariant());
            orderAllItem.add(orderItem);
        });
        orderDetailsRepository.saveAll(orderAllItem);
        cartService.cartClear();
        return orderAllItem;
    }

    public Optional<OrderDetails> getOrderDetail(Long id){
        Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
        return orderDetails;
    }

    public List<OrderDetails> findByUesrOrder(UserOrder order) {
        List<OrderDetails> orderDetails = orderDetailsRepository.findByUesrOrder(order);
        return orderDetails;
    }

    public Optional<UserOrder> getOrder(Long id) {
        Optional<UserOrder> order = orderRepository.findById(id);
        return order;
    }

    public UserOrder orderStatus(Long id, String status) {
        System.out.println(id);
        Optional<UserOrder> order = orderService.getOrder(id);
        UserOrder orderInfo = order.get();
        Status updateStatus = getStatus(status);
        orderInfo.setStatus(updateStatus);
        UserOrder updatedOrder = orderRepository.save(orderInfo);
        return updatedOrder;
    }

    private Status getStatus(String status){
        return Status.valueOf(status);
    }

    public List<?> getUserOrder(User user){
        List<?> orderList = orderRepository.findByUser(user);
        return orderList;
    }

    public Long getLastOrderNO() {
        List<UserOrder> userList = orderRepository.findAll();
        int Size = userList.size();
        UserOrder userOrder = userList.get(Size-1);
        Long init = (long) 1;
        return userOrder.getOrderNo() == null ? init :userOrder.getOrderNo();

    }
}
