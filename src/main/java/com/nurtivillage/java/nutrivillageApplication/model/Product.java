package com.nurtivillage.java.nutrivillageApplication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    // private Categorie categorie
    private String brand;
    @Column(columnDefinition="INT(1) default 0 COMMENT '1 for highlighter, 0 for remove form highlighter'")
    private int highligter;
    private String image;
    private String description;

    public Product(){

    }

    public Product(String brand,int highlighter,String image,String name){
        this.brand = brand;
        this.highligter = highlighter;
        this.image = image;
        this.name = name;
    }


    public String getBrand() {
        return brand;
    }
    public int getHighligter() {
        return highligter;
    }
    public Long getId() {
        return id;
    }
    public String getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
}
