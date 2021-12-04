package com.nurtivillage.java.nutrivillageApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.nurtivillage.java.nutrivillageApplication.model.ShippingAddress;
import com.nurtivillage.java.nutrivillageApplication.service.ShippingAddressService;

@RestController
public class ShippingController {
@Autowired
ShippingAddressService shippingService;

@RequestMapping("/AddShippingAddress")
public ResponseEntity<?> addAddress(@RequestBody ShippingAddress address) {
	try {
	ShippingAddress sa = shippingService.addAddress(address);
	 return new ResponseEntity<>(sa, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
}