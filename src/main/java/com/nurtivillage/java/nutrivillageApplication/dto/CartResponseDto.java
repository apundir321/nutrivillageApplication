package com.nurtivillage.java.nutrivillageApplication.dto;

import com.nurtivillage.java.nutrivillageApplication.model.Product;

public interface CartResponseDto {
    Long getQuantity();
    Product getProduct();
    Long getId();
}
