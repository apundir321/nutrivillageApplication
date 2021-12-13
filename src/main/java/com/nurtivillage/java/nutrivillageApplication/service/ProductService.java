package com.nurtivillage.java.nutrivillageApplication.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.amazonaws.services.simplesystemsmanagement.model.GetInventoryRequest;
import com.nurtivillage.java.nutrivillageApplication.dao.CategoryRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.InventoryRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.ProductRepository;
import com.nurtivillage.java.nutrivillageApplication.dao.VariantRepository;
import com.nurtivillage.java.nutrivillageApplication.model.Category;
import com.nurtivillage.java.nutrivillageApplication.model.Inventory;
import com.nurtivillage.java.nutrivillageApplication.model.Product;
import com.nurtivillage.java.nutrivillageApplication.model.Variant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VariantRepository variantRepo;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryService inventoryService;

    public List<Product> getAllProduct(){
        try {
            List<Product> allProduct = productRepository.
            findByDeletedAtIsNull();
            allProduct.forEach((var)->{
                List<Variant> variants = var.getVariants();
                variants.forEach((v)->{
                    Inventory variantInventory = inventoryRepository.findByProductIdAndVariantId(var.getId(),v.getId());
                    v.setPrice(variantInventory.getPrice());
                    v.setQuantity( variantInventory.getQuantity());
                });
            });
            return allProduct;
        } catch (Exception e) {
            throw e;
        }
    }

    public Product insertProduct(Product product){
        try {            
            Category c = product.getCategory();
            if(!categoryRepository.existsById(c.getId())){
                Category createCat = categoryRepository.save(c);
                product.setCategory(createCat);
            }else{
                product.setCategory(c);
            }
            List<Variant> savedVariants = new ArrayList<>();
            List<Variant> variants = product.getVariants();
            variants.forEach((var)->{
                System.out.println(var.getPrice());
                if(var.getId() == 0){
                    Variant v =   variantRepo.save(var);        
                    savedVariants.add(v);
                }else{
                    Optional<Variant> ov = variantRepo.findById(var.getId());
                    if(ov.isPresent()){
                        savedVariants.add(ov.get());
                    }
                }
            });
            
            product.setVariants(savedVariants);
            Product save = productRepository.save(product);
            save.getVariants().forEach((var)->{
                Optional<Variant> ov = variantRepo.findById(var.getId());
                Variant v = ov.get();
                int quantity = 0;
                int price = 0;
                for(Variant savedVariant : variants)
                {
                    if(savedVariant.getId() != var.getId() && savedVariant.getName() != var.getName()){
                        continue;
                    }
                    quantity = savedVariant.getQuantity();
                    price = savedVariant.getPrice();
                }
                System.out.println(price);
                Inventory inventory = new Inventory(save,v,quantity,price);

                inventoryService.addInventory(inventory);
            });

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
            List<Variant> variantList = productInfo.get().getVariants();
                variantList.forEach((var)->{
                   Inventory variantInventory = inventoryRepository.findByProductIdAndVariantId(id,var.getId());
                   var.setPrice(variantInventory.getPrice());
                   var.setQuantity( variantInventory.getQuantity());
                });
            return productInfo;            
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Product> highlighterProduct() {
        List<Product> productList = productRepository.findByStatusAndDeletedAtIsNull(1);
        return productList;
    }

    public List<Product> categoryProductLIst(Integer categoryId) throws Exception {
        try {
            if(!categoryRepository.existsById(categoryId)){
                throw new ExceptionService("Category is not exists");
            }
            List<Product> productList = productRepository.findByCategoryIdAndDeletedAtIsNull(categoryId);
            return productList;
        } catch (Exception e) {
            throw e;
        }
    }


    // public List<Product> Filter


    // public List<Product> highlighterProduct(){
        // List<Product> highlighter = productRepository.findByHighlighter(1);
        // return highlighter;
    // }
}
