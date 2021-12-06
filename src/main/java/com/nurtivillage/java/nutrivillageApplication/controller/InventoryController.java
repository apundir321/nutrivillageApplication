package com.nurtivillage.java.nutrivillageApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
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
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@RequestMapping("/AddInventory")
public ResponseEntity<?> addInventory(@RequestBody Inventory inventory) {
	try {
	Inventory inv = inventoryService.addInventory(inventory);
	 return new ResponseEntity<Inventory>(inv, HttpStatus.OK);
	}catch (Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
}
