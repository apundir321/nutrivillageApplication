package com.nurtivillage.java.nutrivillageApplication.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
	@NotBlank
    private String name;
	@NotBlank
    private String brand;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	@JoinColumn(name = "category_id")
	@NotEmpty
    private Category category;
	@NotEmpty
    private int status; 
	@NotBlank
    private String image;
	private Date deletedAt;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date updateAt;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdAt;
	@Transient
	private List<Review> review;
	public Long getId() {
		return id;
	}

	@Transient
	private List<Inventory> variant;
	public List<Inventory> getVariant() {
		return variant;
	}

	public void setVariant(List<Inventory> variant) {
		this.variant = variant;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public void setId(Long id) {
		this.id = id;
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
	public int getStatus() {
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
		return "Product [id=" + id + ", name=" + name + ", brand=" + brand + ", status=" + status + ", image=" + image
				+ "]";
	}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<Review> getReview(){
		return review;
	}

	public Category getCategory() {
		return category;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	
	// public Inventory getInventory() {
	// 	return inventory;
	// }
}
