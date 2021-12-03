package com.nurtivillage.java.nutrivillageApplication.dao;

import com.nurtivillage.java.nutrivillageApplication.model.UserOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<UserOrder,Long>{
    
}
