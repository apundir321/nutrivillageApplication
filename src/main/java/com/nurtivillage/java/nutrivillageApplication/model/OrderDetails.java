package com.nurtivillage.java.nutrivillageApplication.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //ordere id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private UserOrder uesrOrder;
    private Long quantity;

    // private Inventory

    public OrderDetails(){}

    public OrderDetails(Product product,UserOrder userOrder,Long quantity){
        this.product = product;
        this.uesrOrder = userOrder;
    }
    
}
