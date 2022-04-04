package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nurtivillage.java.nutrivillageApplication.dao.OrderDetailsRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.OrderRepository;
import com.nurtivillage.java.nutrivillageApplication.dto.StatusRequest;
import com.nurtivillage.java.nutrivillageApplication.model.Cart;
import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Offer;
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

    @Autowired
    public InventoryService inventoryService;

    @Autowired 
    public OfferService offerService;
    
    double totalAmount = 0;

    public List<UserOrder> getAllOrder(){
        List<UserOrder> userOrder = orderRepository.findByStatusNotOrderByCreatedAtAsc(Status.canceled);
        return userOrder;
    }

    public UserOrder createOrder(UserOrder order){
        UserOrder orderCreate = orderRepository.save(order);
        return orderCreate;
    }

    public List<OrderDetails> createOrderDetails(List<Cart> cartItems,UserOrder order){//(List<Product> product,UserOrder order,List<Long> quantity){
        List<OrderDetails> orderAllItem = new ArrayList<>();
        cartItems.forEach((var)->{
            Cart cartItem = cartService.cartItemById(var.getId());
            List<Offer> offer = offerService.getOffersByProduct(cartItem.getProduct().getId());
            if(offer.size() != 0){
                OrderDetails orderItem = new OrderDetails(cartItem.getProduct(),order,cartItem.getQuantity(),cartItem.getVariant(),offer.get(0));
                orderAllItem.add(orderItem);
            }else{
                OrderDetails orderItem = new OrderDetails(cartItem.getProduct(),order,cartItem.getQuantity(),cartItem.getVariant(),null);
                orderAllItem.add(orderItem);
            }
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

    public UserOrder orderStatus(StatusRequest statusRequest) {
        Optional<UserOrder> order = orderService.getOrder(statusRequest.getId());
        UserOrder orderInfo = order.get();
        Status updateStatus = getStatus(statusRequest.getStatus());
        orderInfo.setStatus(updateStatus);
        orderInfo.setComment(statusRequest.getComment());
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

    public List<UserOrder> getAllCancelOrder() {
        List<UserOrder> userOrder = orderRepository.findByStatusOrderByCreatedAtAsc(Status.canceled);
        return userOrder;
    }

    public List<UserOrder> getUserCancelOrder(Long userId) {
        List<UserOrder> userOrder = orderRepository.findByStatusAndUserIdOrderByCreatedAtAsc(Status.canceled,userId);
        return userOrder;
    }

    public boolean amountVarify(double amount,List<Cart> cartItems){
        totalAmount = 0;
        cartItems.forEach(cartinfo->{
            System.out.println(cartinfo.getId());
            Cart cartItem = cartService.cartItemById(cartinfo.getId());
            double price =cartItem.getInventory().getPrice();
            totalAmount = totalAmount + price;
        });
        if(totalAmount != amount){
            return false;
        }
        return true;
    }
}
