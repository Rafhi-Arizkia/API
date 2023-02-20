package com.web.api.service;

import com.web.api.model.entities.ProductEntities;
import com.web.api.model.entities.SupplierEntities;
import com.web.api.model.repo.ProductRepo;
import com.web.api.model.repo.SupplierRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
public class ProductService {

    private final ProductRepo productRepo;
    private  SupplierRepo supplierRepo;
    @Autowired
    public void setSupplierRepo(SupplierRepo supplierRepo){
        this.supplierRepo = supplierRepo;
    }

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public ProductEntities saveProduct(ProductEntities productEntities){
        return productRepo.save(productEntities);
    }

    public Iterable<ProductEntities> getAllProduct(){
        return productRepo.findAll();
    }

    public void getDeleteById(Long productId){
        productRepo.deleteById(productId);
    }

    public void addSupplier(SupplierEntities supplierEntities, Long productId){
        ProductEntities productEntities = productRepo.findById(productId)
                .orElseThrow(()-> new RuntimeException("Invalid product Id:" + productId));
        Set<SupplierEntities> supplierEntitiesSet = productEntities.getSupplierEntities();
        supplierEntitiesSet.add(supplierEntities);
        productEntities.getSupplierEntities().add(supplierEntities);
        supplierEntities.getProductEntities().add(productEntities);
    }


}
