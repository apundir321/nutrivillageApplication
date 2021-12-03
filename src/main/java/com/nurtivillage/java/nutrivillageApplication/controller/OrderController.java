package com.nurtivillage.java.nutrivillageApplication.controller;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.Request.OrderRequest;
import com.nurtivillage.java.nutrivillageApplication.model.OrderDetails;
import com.nurtivillage.java.nutrivillageApplication.model.Status;
import com.nurtivillage.java.nutrivillageApplication.model.UserOrder;
import com.nurtivillage.java.nutrivillageApplication.service.ApiResponseService;
import com.nurtivillage.java.nutrivillageApplication.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class OrderController {
        @Autowired
        public OrderService orderService;
        @GetMapping("/list")
        public ResponseEntity<ApiResponseService> allOrder(){
            try{
                List<UserOrder> orderList = orderService.getAllOrder();
                ApiResponseService res = new ApiResponseService("order List",true,orderList);
                return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
            }catch(Exception e){
                System.out.println(e);
                ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("error"));
                return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/create")
        public ResponseEntity<ApiResponseService> createOrder(@RequestBody OrderRequest orderRequest){
            try{
                Long orderNO = (long) 0002;
                UserOrder order = new UserOrder(orderRequest.getAmount(),orderRequest.getUser(),orderNO,orderRequest.getItem().size(),Status.ordered);
                UserOrder orderCreate = orderService.createOrder(order);
                ApiResponseService res = new ApiResponseService("orderStatus",true,List.of(orderCreate.getId()));
                return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
            }catch(Exception e){
                System.out.println(e);
                ApiResponseService res = new ApiResponseService(e.getMessage(),false,List.of("error"));
                return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } 
}
