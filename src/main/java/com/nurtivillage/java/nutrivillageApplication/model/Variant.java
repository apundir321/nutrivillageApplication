package com.nurtivillage.java.nutrivillageApplication.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import javax.persistence.Transient;



@Entity
public class Variant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Transient
	private int price; 
	@Transient
	private int quantity;
	// @ManyToMany(fetch=FetchType.LAZY,mappedBy="variants")
	// private List<Product> products;

	public Variant() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}

	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// public List<Product> getProducts() {
	// 	return products;
	// }
	// public void setProducts(List<Product> products) {
	// 	this.products = products;
	// }
	@Override
	public String toString() {
		return "Variant [vid=" + id + ", name=" + name + ", products=]";
	}
	
	
	

}
