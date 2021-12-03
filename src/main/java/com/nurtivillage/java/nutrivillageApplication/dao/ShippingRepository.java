package com.nurtivillage.java.nutrivillageApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nurtivillage.java.nutrivillageApplication.model.ShippingAddress;

public interface ShippingRepository extends JpaRepository<ShippingAddress,Integer> {

}
