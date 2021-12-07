package com.nurtivillage.java.nutrivillageApplication.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    @Column(nullable = false)
    private String name;
    private String brand;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
    private Category category;
    private String status;
	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.MERGE)
	  @JoinTable(
		        name = "products_variant",
		        joinColumns = {
		            @JoinColumn(name = "pid")
		        },
		        inverseJoinColumns = {
		            @JoinColumn(name = "vid")
		        }
		    )
    private List<Variant> variants;
    private int price;
    private String image;

	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", name=" + name + ", brand=" + brand + ", category=" + category + ", status="
				+ status + ", variants=" + variants + ", price=" + price + ", image=" + image + "]";
	}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
}
