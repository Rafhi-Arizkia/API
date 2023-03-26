package com.web.api.service;

import com.web.api.model.entities.ProductEntities;
import com.web.api.model.entities.SupplierEntities;
import com.web.api.model.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;

@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductService {

    private final ProductRepo productRepo;
    private final SupplierService supplierService;

    @Autowired
    public ProductService(ProductRepo productRepo, SupplierService supplierService) {
        this.productRepo = productRepo;
        this.supplierService = supplierService;
    }

    @CachePut(value = "product", key = "#productEntities.productName")
    // cache put untuk menandai method yang akan di save atau update
    public ProductEntities saveProduct(ProductEntities productEntities) {
        if (productEntities.getProductId() != null) {
            // untuk melakukan update
            ProductEntities productEntities1 = productRepo.findById(productEntities.getProductId())
                    .orElseThrow(() -> new NotFoundException("" +
                            "{\"error\":\"Not found product with Id\"" + productEntities.getProductId() + "\"}"));
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

    @Cacheable(value = "product", key = "productId") // anotasi ini digunakan untuk menandai method yang akan di-cache.
    // Ketika method ini dipanggil dengan parameter yang sama, maka hasil dari method tersebut akan diambil dari cache
    // dan tidak dieksekusi lagi
    public ProductEntities findById(Long productId) {
        Optional<ProductEntities> productEntities = productRepo.findById(productId);
        if (productEntities.isEmpty()) {
            throw new RuntimeException("Invalid product Id:" + productId);
        }
        return productEntities.get();
    }

    @CacheEvict(value = "product", allEntries = true)
//    anotasi ini digunakan untuk menandai method yang akan menghapus data pada cache. Ketika method ini dipanggil,
//    maka data pada cache dengan key yang sesuai akan dihapus. Anotasi ini memiliki parameter value yang menunjukkan
//    nama cache yang digunakan untuk menyimpan data, dan parameter allEntries yang menunjukkan apakah semua data pada
//    ache akan dihapus atau hanya data dengan key tertentu saja.
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
