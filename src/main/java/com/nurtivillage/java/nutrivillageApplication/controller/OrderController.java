package com.nurtivillage.java.nutrivillageApplication.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.nurtivillage.java.nutrivillageApplication.RazorPayClientConfig;
import com.nurtivillage.java.nutrivillageApplication.Request.OrderRequest;
import com.nurtivillage.java.nutrivillageApplication.dto.StatusRequest;
import com.nurtivillage.java.nutrivillageApplication.model.OrderDetails;
import com.nurtivillage.java.nutrivillageApplication.model.Payment;
import com.nurtivillage.java.nutrivillageApplication.model.Status;
import com.nurtivillage.java.nutrivillageApplication.model.User;
import com.nurtivillage.java.nutrivillageApplication.model.UserOrder;
import com.nurtivillage.java.nutrivillageApplication.service.ApiResponseService;
import com.nurtivillage.java.nutrivillageApplication.service.LoggedInUserService;
import com.nurtivillage.java.nutrivillageApplication.service.OnlinePaymentService;
import com.nurtivillage.java.nutrivillageApplication.service.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

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
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/order")
public class OrderController {
        @Autowired
        public OrderService orderService;
        @Autowired
        public LoggedInUserService userService;
        @Autowired 
        public OnlinePaymentService onlinePaymentService;
        private RazorpayClient razorpayClient;
        private RazorPayClientConfig razorpayClientConfig;
        
        @Autowired
        public OrderController(RazorPayClientConfig razorpayClientConfig) throws RazorpayException{
        	this.razorpayClientConfig=razorpayClientConfig;
        	this.razorpayClient=new RazorpayClient(razorpayClientConfig.getKey(),razorpayClientConfig.getSecret());
        }
        
        
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

        @GetMapping("/cancel")
        public ResponseEntity<ApiResponseService> cancelOrder(){
            try{
                List<UserOrder> orderList = orderService.getAllCancelOrder();
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
                UserOrder order = new UserOrder(amount,user,orderNO+1,orderRequest.getCartItem().size(),Status.ordered,orderRequest.getShippingAddress(),orderRequest.getPaymentMethod());
                UserOrder orderCreate = orderService.createOrder(order);
                List<OrderDetails> data = orderService.createOrderDetails(orderRequest.getCartItem(),orderCreate);
                if(!orderRequest.getPaymentMethod().equals("COD")){
                    Order orderRes = onlinePaymentService.createOrderOnRazorpay(order,this.razorpayClient);
                    onlinePaymentService.savePayment(orderRes.get("id"), order);
                    ApiResponseService res = new ApiResponseService("make payment",true,Arrays.asList(orderRes.get("id"),orderRes.get("amount")));
                    return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
                }
                System.out.print(data);
                ApiResponseService res = new ApiResponseService("order placed",true,data);
                return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
            }catch(Exception e){
                System.out.println(e);
                ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
                return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } 
        
        @PutMapping("/validatePayment")
        public ResponseEntity<ApiResponseService> updateOrder(@RequestBody Payment payment){
        	try {
        		String error=onlinePaymentService.validateAndUpdateOrder(payment.getRazopayOrderId(),payment.getRazorpayPaymentId(),payment.getRazorpaySignature(),razorpayClientConfig.getSecret());
        	     if(error!=null) {
                    ApiResponseService res = new ApiResponseService("something went wrong",false,Arrays.asList("error"));
        	    	return new ResponseEntity<ApiResponseService>(res,HttpStatus.BAD_REQUEST);
        	     }
                 ApiResponseService res = new ApiResponseService("ok",true,Arrays.asList());
        	     return new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        	}
        	catch(Exception e) {
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

        @PutMapping("status")
        public ResponseEntity<ApiResponseService> orderStatus(@RequestBody StatusRequest statusRequest){
            try{
                UserOrder orderCreate = orderService.orderStatus(statusRequest);
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

        @GetMapping(value="/usercancelorderList")
        public ResponseEntity<ApiResponseService> getUserCancelOrder(){
            try {
                User user = userService.userDetails();
                List<UserOrder> orderList = orderService.getUserCancelOrder(user.getId());
                ApiResponseService res = new ApiResponseService("order List",true,orderList);
                return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
            } catch (Exception e) {
                System.out.println(e);
                ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
                return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PutMapping("/userodercancel")
        public ResponseEntity<ApiResponseService> userOrderCancel(@RequestBody StatusRequest statusRequest){
            try{
                statusRequest.setStatus("canceled");
                UserOrder orderCreate = orderService.orderStatus(statusRequest);
                ApiResponseService res = new ApiResponseService("orderStatus",true,Arrays.asList(orderCreate));
                return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
            }catch(Exception e){
                System.out.println(e);
                ApiResponseService res = new ApiResponseService(e.getMessage(),false,Arrays.asList("error"));
                return new ResponseEntity<ApiResponseService>(res,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } 

        // @GetMapping(value="/refund/{order_id}")
        // public ResponseEntity<ApiResponseService> getMethodName(@PathVariable Long order_id) {
        //     try {
        //         Optional<UserOrder> order = orderService.getOrder(order_id);
        //         if(order.isEmpty()){
        //             new Exception("order not found");
        //         }
        //         onlinePaymentService.refund(order_id);
        //         // UserOrder orderCreate = orderService.orderStatus(statusRequest);
        //         ApiResponseService res = new ApiResponseService("orderStatus",true,Arrays.asList(orderCreate));
        //         return  new ResponseEntity<ApiResponseService>(res,HttpStatus.OK);
        //     } catch (Exception e) {
        //         throw e;
        //     }
        // }
        

}
