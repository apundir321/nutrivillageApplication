package com.nurtivillage.java.nutrivillageApplication.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Review {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   @OneToOne(cascade = CascadeType.MERGE)
   @JoinColumn(name = "user_id",nullable = false)
   private User user;
   @OneToOne(cascade = CascadeType.MERGE)
   @JoinColumn(name = "product_id",nullable = false)
   private Product product;
   private String comment;
   private String rating;

   public Review(){

   }

   public Review(String comment,String rating,User user){
    //    this.product = product;
       this.user = user;
       this.rating = rating;
       this.comment = comment;
   }

   public String getComment() {
       return comment;
   }
   public Long getId() {
       return id;
   }
//    public Product getProduct() {
//        return product;
//    }
   public String getRating() {
       return rating;
   }
   public User getUser() {
       return user;
   }
}
