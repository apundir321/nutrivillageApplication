package com.nurtivillage.java.nutrivillageApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nurtivillage.java.nutrivillageApplication.dao.ShippingRepository;
import com.nurtivillage.java.nutrivillageApplication.model.ShippingAddress;

@Service
public class ShippingAddressService {
	
	@Autowired
	ShippingRepository shippingRepository;
public ShippingAddress addAddress(ShippingAddress sa) {
	try {
		ShippingAddress s= shippingRepository.save(sa);
		return s;
	}
	catch(Exception e) {
		throw e;
	}}
	public ShippingAddress getAddress(ShippingAddress sa) {
		try {
			ShippingAddress s= shippingRepository.save(sa);
			return s;
		}
		catch(Exception e) {
			throw e;
		}
			
}
}
