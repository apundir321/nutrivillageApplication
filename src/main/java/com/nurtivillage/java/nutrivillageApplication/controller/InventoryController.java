package com.nurtivillage.java.nutrivillageApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.Variant;
import com.nurtivillage.java.nutrivillageApplication.service.InventoryService;

@RestController
public class InventoryController {
@Autowired
InventoryService inventoryService;

@RequestMapping("/getAllInventory")
public ResponseEntity<?> allInventory() {
	try {
	List<Inventory> inv= inventoryService.getAll();
	 return new ResponseEntity<>(inv, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@RequestMapping("/AddInventory")
public ResponseEntity<?> addInventory(@RequestBody Inventory inventory) {
	try {
	Inventory inv = inventoryService.addInventory(inventory);
	 return new ResponseEntity<Inventory>(inv, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/getProductInventory")
public ResponseEntity<?> getProductInventory(@RequestBody Product product) {
	try {
Inventory inv= inventoryService.getProductInventory(product);
	 return new ResponseEntity<>(inv, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/updateInventory/{product_id}/{variant}/{quantity}")
public ResponseEntity<?> updateInventory(@PathVariable Product product,@PathVariable Variant variant,@PathVariable int quantity) {
	try {
Inventory inv= inventoryService.updateInventory(product,variant,quantity);
	 return new ResponseEntity<>(inv, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
}
