package com.nurtivillage.java.nutrivillageApplication.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private double amount;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_Ad_id")
    private ShippingAddress shippingAddress;

    @Column(nullable = false,unique = true)
    private Long orderNo;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private int itemNO;

    @Lob
    private String comment;

    @UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
    
    public UserOrder(){}

    public UserOrder(double amount,User user,Long orderNO,int itemNO,Status status,ShippingAddress shippingAddress){
        this.amount = amount;
        this.orderNo = orderNO;
        this.user = user;
        this.itemNO = itemNO;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public double getAmount() {
        return amount;
    }
    public Long getId() {
        return id;
    }
    public Long getOrderNo() {
        return orderNo;
    }

    public String getComment() {
        return comment;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public User getUser() {
        return user;
    }
    public int getItemNO(){
        return itemNO;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}

