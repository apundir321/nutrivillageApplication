package com.nurtivillage.java.nutrivillageApplication.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String razorpayPaymentId;
    private String razopayOrderId;
    private String razopaySignature;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	@JoinColumn(name = "order_id")
    private UserOrder order;

    public Payment(){};
    public Payment(String razorpayPaymentId,String razopayOrderId,String razorpaySignature,UserOrder order){
        this.razorpayPaymentId = razorpayPaymentId;
        this.razopayOrderId = razopayOrderId;
        this.razopaySignature = razopaySignature;
        this.order = order;

    }

    public UserOrder getOrder() {
        return order;
    }

    public String getRazopayOrderId() {
        return razopayOrderId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public String getRazopaySignature() {
        return razopaySignature;
    }

}
