package com.nurtivillage.java.nutrivillageApplication.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @Column(nullable = false,unique = true)
    private Long orderNo;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private int itemNO;
    // private Shipping shipping
    //private Payment payment;
    //private order_details
    public UserOrder(){}

    public UserOrder(double amount,User user,Long orderNO,int itemNO,Status status){
        this.amount = amount;
        this.orderNo = orderNO;
        this.user = user;
        this.itemNO = itemNO;
        this.status = status;
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
    public Status getStatus() {
        return status;
    }
    public User getUser() {
        return user;
    }
    public int getItemNO(){
        return itemNO;
    }
}

