package com.nurtivillage.java.nutrivillageApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

import com.nurtivillage.java.nutrivillageApplication.dto.InventoryResponse;
import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.Variant;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
	// Inventory findByProduct(Product product);

	Inventory findByProductAndVariant(Product product, Variant variant);

    List<Inventory> findByProduct(Product product);

	List<InventoryResponse> findByProductId(Long id);

    Inventory findByProductIdAndVariantId(Long id, int id2);
}
