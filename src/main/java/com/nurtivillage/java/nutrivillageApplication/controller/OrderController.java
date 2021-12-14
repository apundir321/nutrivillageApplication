package com.nurtivillage.java.nutrivillageApplication.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.nurtivillage.java.nutrivillageApplication.Request.OrderRequest;
import com.nurtivillage.java.nutrivillageApplication.model.OrderDetails;
import com.nurtivillage.java.nutrivillageApplication.model.Status;
import com.nurtivillage.java.nutrivillageApplication.model.User;
import com.nurtivillage.java.nutrivillageApplication.model.UserOrder;
import com.nurtivillage.java.nutrivillageApplication.security.UserService;
import com.nurtivillage.java.nutrivillageApplication.service.ApiResponseService;
import com.nurtivillage.java.nutrivillageApplication.service.LoggedInUserService;
import com.nurtivillage.java.nutrivillageApplication.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class OrderController {
        @Autowired
        public OrderService orderService;
        @Autowired
        public LoggedInUserService userService;
        @GetMapping("/list")
        public ResponseEntity<ApiResponseService> allOrder(){
            try{
                List<UserOrder> orderList = orderService.getAllOrder();
                ApiResponseService res = new ApiResponseService("order List",true,orderList);
                return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
            }catch(Exception e){
                System.out.println(e);
                ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
                return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/create")
        public ResponseEntity<ApiResponseService> createOrder(@RequestBody OrderRequest orderRequest){
            try{
                Long orderNO = orderService.getLastOrderNO();
                double amount = orderRequest.getAmount();
                User user = userService.userDetails();
                UserOrder order = new UserOrder(amount,user,orderNO+1,orderRequest.getCartItem().size(),Status.ordered);
                UserOrder orderCreate = orderService.createOrder(order);
                List<OrderDetails> data = orderService.createOrderDetails(orderRequest.getCartItem(),orderCreate);
                System.out.print(data);
                ApiResponseService res = new ApiResponseService("orderStatus",true,data);
                return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
            }catch(Exception e){
                System.out.println(e);
                ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
                return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } 

        @GetMapping("/detail/{id}")
        public ResponseEntity<ApiResponseService> orderDetail(@PathVariable Long id){
            try{
                Optional<UserOrder> order = orderService.getOrder(id);
                List<OrderDetails> orderDetails = orderService.findByUesrOrder(order.get());
                
                ApiResponseService res = new ApiResponseService("order detail",true,orderDetails);
                return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
            }catch(Exception e){
                System.out.println(e);
                ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
                return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PutMapping("/{status}/{id}")
        public ResponseEntity<ApiResponseService> orderStatus(@PathVariable String status,@PathVariable Long id){
            try{
                System.out.println(id);
                UserOrder orderCreate = orderService.orderStatus(id,status);
                ApiResponseService res = new ApiResponseService("orderStatus",true,Arrays.asList(orderCreate));
                return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
            }catch(Exception e){
                System.out.println(e);
                ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
                return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/user/list")
        public ResponseEntity<ApiResponseService> userOrderList(){
            try{
                User user = userService.userDetails();
                List<?> order = orderService.getUserOrder(user);
                
                ApiResponseService res = new ApiResponseService("User order list",true,order);
                return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
            }catch(Exception e){
                System.out.println(e);
                ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
                return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
