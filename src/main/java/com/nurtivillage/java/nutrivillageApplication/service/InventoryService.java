package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nurtivillage.java.nutrivillageApplication.dao.InventoryRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.Variant;


@Service
public class InventoryService {
	@Autowired 
	InventoryRepository inventoryRepo;
	public List<Inventory> getAll(){
		try {
			return inventoryRepo.findAll();
			
		}
		catch(Exception e) {
			throw e;
		}
	}
	public Inventory addInventory(Inventory inventory) {
		try {
			Inventory inv=inventoryRepo.save(inventory);
			return inv;
		}
		catch(Exception e) {
			throw e;
		}
	}

	public List<Inventory> getProductInventory(Product product) {
		try {
			List<Inventory> inv=inventoryRepo.findByProduct(product);
	return inv;
		}
		catch(Exception e) {
			throw e;
		}
	}
	public Inventory getProductVariantInventory(int  productId,int  variantId) {
		try {
			Inventory inv=inventoryRepo.findByProductIdAndVariantId(productId,variantId);
	return inv;
		}
		catch(Exception e) {
			throw e;
		}
	}
	public Inventory updateInventory(int productId,int variantId,int quantity) {
		try {
			Inventory inv=inventoryRepo.findByProductIdAndVariantId(productId,variantId);
			if(inv.getQuantity()>=quantity) {
			inv.setQuantity(inv.getQuantity()-quantity);
			inventoryRepo.save(inv);}
			return inv;
		}
		catch(Exception e) {

			throw e;
		}
	}
}
