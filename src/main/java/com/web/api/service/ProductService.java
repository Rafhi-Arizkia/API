package com.web.api.service;

import com.web.api.model.entities.ProductEntities;
import com.web.api.model.entities.SupplierEntities;
import com.web.api.model.repo.ProductRepo;
import com.web.api.model.repo.SupplierRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void saveProduct(ProductEntities productEntities){
        productRepo.save(productEntities);
    }

    public Iterable<ProductEntities> getAllProduct(){
        return productRepo.findAll();
    }

    public void setDeleteById(Long productId){
        productRepo.deleteById(productId);
    }

    public void addSupplierToProduct(Long productId,Long supplierId){
        ProductEntities productEntities = productRepo.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("Product not found"));
        SupplierEntities supplierEntities= supplierRepo.findById(supplierId)
                .orElseThrow(()-> new IllegalArgumentException("Supplier not found"));
        productEntities.getSupplierEntities().add(supplierEntities);
        productRepo.save(productEntities);
    }
}
