package com.nurtivillage.java.nutrivillageApplication.service;


import com.nurtivillage.java.nutrivillageApplication.model.UserOrder;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class OnlinePaymentService {
    public Order createOrderOnRazorpay(UserOrder order) throws Exception{
        RazorpayClient client = null;
        String razorpayOrderId = null;
        try {
            client = new RazorpayClient("rzp_test_Lp2CeDDsYiDQLy","YfElYqtH9V1abgd1ledlRmZh");
            JSONObject options = new JSONObject();
            options.put("amount", order.getAmount());
            options.put("currency", "INR");
            options.put("receipt","order_"+order.getId());
            Order orderRes = client.Orders.create(options);
            return orderRes;
        } catch (Exception e) {
            throw e;
        }
    }
}
