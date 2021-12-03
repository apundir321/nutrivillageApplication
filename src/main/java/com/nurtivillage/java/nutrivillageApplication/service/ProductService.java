package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.List;

import com.nurtivillage.java.nutrivillageApplication.dao.ProductRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct(){
        List<Product> allProduct = productRepository.findAll();
        return allProduct;
    }

    public Product insertProduct(Product product){
        Product save = productRepository.save(product);
        return save;
    }

    public String DeleteProduct(Long id){
        productRepository.deleteById(id);
        return "delete product";
    }

    // public List<Product> highlighterProduct(){
        // List<Product> highlighter = productRepository.findByHighlighter(1);
        // return highlighter;
    // }
}
