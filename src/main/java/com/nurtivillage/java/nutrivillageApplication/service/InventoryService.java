package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nurtivillage.java.nutrivillageApplication.dao.InventoryRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Product;

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

	public List<Inventory> productInventory(Product product){
		try{
			List<Inventory> in = inventoryRepo.findByProduct(product);
			return in;
		}catch(Exception e){
			throw e;
		}
	}
}
