package com.nurtivillage.java.nutrivillageApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nurtivillage.java.nutrivillageApplication.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

}
