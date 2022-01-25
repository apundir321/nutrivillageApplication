package com.nurtivillage.java.nutrivillageApplication.service;



import com.nurtivillage.java.nutrivillageApplication.dao.OrderRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.PaymentRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Payment;
import com.nurtivillage.java.nutrivillageApplication.model.UserOrder;
import com.nurtivillage.java.nutrivillageApplication.model.Signature;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.transaction.Transactional;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlinePaymentService {
	@Autowired
	OrderRepository orderRepository;
    public Order createOrderOnRazorpay(UserOrder order,RazorpayClient client) throws Exception{
        
        String razorpayOrderId = null;
        try {
           String amountInPaise=convertRupeeToPaise(String.valueOf(order.getAmount()));
            JSONObject options = new JSONObject();
            options.put("amount", amountInPaise);
            options.put("currency", "INR");
            options.put("receipt","order_"+order.getId());
            Order orderRes = client.Orders.create(options);
            return orderRes;
        } catch (RazorpayException e) {
            throw e;
        }
    }
	@Autowired
	PaymentRepository paymentRepo;
	@Transactional
	 public Payment savePayment(String razorpayOrderId,UserOrder userOrder) throws Exception {
	  try {  Payment p=new Payment();
	    p.setRazorpayOrderId(razorpayOrderId);
	    p.setOrder(userOrder);
	    return paymentRepo.save(p);}
	  catch(Exception e) {
		  throw e;
	  }
	}
	@Transactional
	public String validateAndUpdateOrder(final String razorpayOrderId,final String razorpayPaymentId,final String razorSignature,final String secret) {
		String error=null;
		try {
			Payment payment=paymentRepo.findByRazorpayOrderId(razorpayOrderId);
	        String generatedSignature = Signature.calculateRFC2104HMAC(payment.getRazopayOrderId() + "|" + razorpayPaymentId, secret);
	        if(generatedSignature.equals(razorSignature)) {
	        	payment.setRazorpayOrderId(razorpayOrderId);
	        	payment.setRazorpaySignature(razorSignature);
	        	payment.setRazorpayPaymentId(razorpayPaymentId);
	        	UserOrder userOrder=payment.getOrder();
	        	userOrder.setPaymentStatus("PAID");
	        	orderRepository.save(userOrder);
	        	
	        	paymentRepo.save(payment);
	        }
	        else {
	        	error="Payment validation failed..Signature Doesn't match.";
	        }
			
		}catch(Exception e) {
			error="Payment validation failed";
		}
		return error;
	}
	public String convertRupeeToPaise(String amount) {
		BigDecimal b=new BigDecimal(amount);
		BigDecimal value=b.multiply(new BigDecimal("100"));
		return value.setScale(0, RoundingMode.UP).toString();
		
	}
}
