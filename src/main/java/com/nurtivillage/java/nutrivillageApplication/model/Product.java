package com.nurtivillage.java.nutrivillageApplication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    // private Categorie categorie
    private String brand;
    @Column(columnDefinition="INT(1) default 0 COMMENT '1 for highlighter, 0 for remove form highlighter'");
    private int highligter;
    private String image;
}
