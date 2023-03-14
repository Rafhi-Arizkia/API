package com.web.api.service;

import com.web.api.model.entities.ProductEntities;
import com.web.api.model.entities.SupplierEntities;
import com.web.api.model.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;

@Service
@Transactional
public class ProductService {

    private final ProductRepo productRepo;
    private final SupplierService supplierService;

    @Autowired
    public ProductService(ProductRepo productRepo, SupplierService supplierService) {
        this.productRepo = productRepo;
        this.supplierService = supplierService;
    }

    public ProductEntities saveProduct(ProductEntities productEntities) {
        if (productEntities.getProductId()!=null){
            // untuk melakukan update
            ProductEntities productEntities1 = productRepo.findById(productEntities.getProductId())
                    .orElseThrow(()-> new NotFoundException("" +
                            "{\"error\":\"Not found product with Id\"" +productEntities.getProductId()+ "\"}"));
            productEntities1.setSupplierProduct(productEntities.getSupplierProduct());
            productEntities1.setCategoryProduct(productEntities.getCategoryProduct());
            productEntities1.setProductName(productEntities.getProductName());
            productEntities1.setProductDescription(productEntities.getProductDescription());
            productEntities1.setProductPrice(productEntities.getProductPrice());

            productEntities1 = productEntities;
        }
        return productRepo.save(productEntities);
    }

    public Iterable<ProductEntities> getAllProduct() {
        return productRepo.findAll();
    }

    public ProductEntities findById(Long productId) {
        Optional<ProductEntities> productEntities = productRepo.findById(productId);
        if (productEntities.isEmpty()) {
            throw new RuntimeException("Invalid product Id:" + productId);
        }
        return productEntities.get();
    }

    public void getDeleteById(Long productId) {
        productRepo.deleteById(productId);
    }

    public void addSupplier(SupplierEntities supplierEntities, Long productId) {
        Optional<ProductEntities> productEntities = productRepo.findById(productId);
        if (productEntities.isPresent()) {
            ProductEntities product = productEntities.get();
            Set<SupplierEntities> supplierEntitiesSet = product.getSupplierProduct();
            supplierEntitiesSet.add(supplierEntities);
            product.setSupplierProduct(supplierEntitiesSet);
            if (supplierEntities.getProductSupplier() == null) {
                supplierEntities.setProductSupplier(new HashSet<>());
            }
            supplierEntities.getProductSupplier().add(product);
            saveProduct(product);
        } else {
            throw new RuntimeException("Invalid product Id:" + productId);
        }
    }

//    Ini menggunakan SQL Query
    public ProductEntities getProductByName(String name) {
        return productRepo.findProductByName(name);
    }

    public List<ProductEntities> getProductByNameLike(String name) {
        return productRepo.findProductByProductNameLike("%" + name + "%");
    }

    public List<ProductEntities> getProductByCategory(Long categoryId) {
        return productRepo.findProductByCategory(categoryId);
    }

    public List<ProductEntities> findBySupplier(Long supplierId) {
        SupplierEntities supplierEntities = supplierService.findSupplierById(supplierId);
        if (supplierEntities == null) {
            return new ArrayList<>();
        }
        return productRepo.findProductEntitiesBySupplierProduct(supplierEntities);
    }
}
