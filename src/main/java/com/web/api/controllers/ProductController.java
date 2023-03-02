package com.web.api.controllers;

import com.web.api.dto.ResponData;
import com.web.api.dto.SearchData;
import com.web.api.model.entities.ProductEntities;
import com.web.api.model.entities.SupplierEntities;
import com.web.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // create new data
    @PostMapping
    public ResponseEntity<ResponData<ProductEntities>> saveProduct
    (@Valid @RequestBody ProductEntities productEntities, Errors errors) {
        ResponData<ProductEntities> responData = new ResponData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responData.getMessages().add(error.getDefaultMessage());
            }
            responData.setStatus(false);
            responData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
        }
        responData.setStatus(true);
        responData.setPayload(productService.saveProduct(productEntities));
        return ResponseEntity.ok(responData);
    }

    @PutMapping// update data
    public ResponseEntity<ResponData<ProductEntities>> updateProduct
            (@Valid @RequestBody ProductEntities productEntities, Errors errors) {
        ResponData<ProductEntities> responData = new ResponData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responData.getMessages().add(error.getDefaultMessage());
            }
            responData.setStatus(false);
            responData.setPayload(null);
            errors.getAllErrors().forEach(error -> responData.getMessages().add(error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
        }
        responData.setStatus(true);
        responData.setPayload(productService.saveProduct(productEntities));
        return ResponseEntity.ok(responData);
    }

    @DeleteMapping("/{productId}")// delete data by id
    public void deleteById(@PathVariable("productId") Long productId) {
        productService.getDeleteById(productId);
    }

    @GetMapping// get all data
    public Iterable<ProductEntities> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/{productId}")// get data by id
    public ProductEntities findById(@PathVariable("productId") Long productId) {
        return productService.findById(productId);
    }

    @PostMapping("/{productId}")// add supplier to product
    public void addSupplierToProduct(@PathVariable("productId") Long productId,
                                     @RequestBody SupplierEntities supplierEntities) {
        productService.addSupplier(supplierEntities, productId);
    }

    @PostMapping("/search/name")
    public ProductEntities getProductByName(@RequestBody SearchData searchData) {
        return productService.getProductByName(searchData.getSearchKey());
    }

    @PostMapping("/search/nameLike")
    public List<ProductEntities> getFindProductByName(@RequestBody SearchData searchData) {
        return productService.getProductByNameLike(searchData.getSearchKey());
    }

    @GetMapping("/search/category/{categoryId}")
    public List<ProductEntities> getProductByCategory(@PathVariable("categoryId") Long categoryId) {
        return productService.getProductByCategory(categoryId);
    }

    @GetMapping("/search/supplier/{supplierId}")
    public List<ProductEntities> getProductBySupplier(@PathVariable("supplierId") Long supplierId) {
        return productService.findBySupplier(supplierId);
    }
}