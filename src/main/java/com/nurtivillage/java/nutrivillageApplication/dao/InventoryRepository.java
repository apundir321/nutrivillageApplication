package com.nurtivillage.java.nutrivillageApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Product;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

    List<Inventory> findByProduct(Product product);
}
