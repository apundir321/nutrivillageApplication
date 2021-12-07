package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.nurtivillage.java.nutrivillageApplication.dao.ProductRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct(){
        try {
            List<Product> allProduct = productRepository.findByDeletedAtIsNull();
            return allProduct;
        } catch (Exception e) {
            throw e;
        }
    }

    public Product insertProduct(Product product){
        try {
            Product save = productRepository.save(product);
            return save;
        } catch (Exception e) {
            throw e;
        }
    }

    public String DeleteProduct(Long id) throws Exception{
        try {
            if(!productRepository.existsById(id)){
                throw new ExceptionService("product is deleted or not exists");
            }
            System.out.println(id);
            Optional<Product> product = productRepository.findById(id);
            product.get().setDeletedAt(new Date());
            productRepository.save(product.get());
            return "delete product";
        } catch (Exception e) {
            throw e;
        }
    }

    public Optional<Product> ProductInfo(Long id) throws Exception {
        try {
            if(!productRepository.existsById(id)){
                throw new ExceptionService("product is not exists");
            }
            Optional<Product> productInfo = productRepository.findById(id);
            if(productInfo.get().getDeletedAt() != null){
                throw new ExceptionService("product is deleted");
            }
            return productInfo;            
        } catch (Exception e) {
            throw e;
        }
    }

    // public List<Product> highlighterProduct(){
        // List<Product> highlighter = productRepository.findByHighlighter(1);
        // return highlighter;
    // }
}
