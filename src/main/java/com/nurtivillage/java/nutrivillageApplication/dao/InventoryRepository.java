package com.nurtivillage.java.nutrivillageApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.Variant;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
	

	Inventory findByProductIdAndVariantId(int product, int variant);

    List<Inventory> findByProduct(Product product);
}
