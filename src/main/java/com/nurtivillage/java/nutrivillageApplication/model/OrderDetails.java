package com.nurtivillage.java.nutrivillageApplication.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id")
    private UserOrder uesrOrder;
    private int quantity;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "variant_id")
    private Variant variant;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "offer_id")
    private Offer offer;
    //private Inventory

    public OrderDetails(){}

    public OrderDetails(Product product,UserOrder userOrder,int i,Variant variant,Offer offer){
        this.product = product;
        this.uesrOrder = userOrder;
        this.quantity = i;
        this.variant = variant;
        this.offer = offer;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Variant getVariant() {
        return variant;
    }

    public Offer getOffer(){
        return offer;
    }

    @JsonIgnore
    public UserOrder getUesrOrder() {
        return uesrOrder;
    }
    
}
