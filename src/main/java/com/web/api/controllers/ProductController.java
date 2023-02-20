package com.web.api.controllers;

import com.web.api.model.dto.ResponData;
import com.web.api.model.entities.ProductEntities;
import com.web.api.model.entities.SupplierEntities;
import com.web.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService ;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping// create new data
    public ResponseEntity<ResponData<ProductEntities>>saveProduct
            (@Valid  @RequestBody ProductEntities productEntities, Errors errors) {
        ResponData<ProductEntities> responData = new ResponData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responData.getMessages().add(error.getDefaultMessage());
            }
            responData.setStatus(false);
            responData.setPayload(null);
            return ResponseEntity.badRequest().body(responData);
        }
        responData.setStatus(true);
        responData.setPayload(productService.saveProduct(productEntities));
        return ResponseEntity.ok(responData);
    }
    @PutMapping// update data
    public ResponseEntity<ResponData<ProductEntities>>updateProduct
            (@Valid  @RequestBody ProductEntities productEntities, Errors errors) {
        ResponData<ProductEntities> responData = new ResponData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responData.getMessages().add(error.getDefaultMessage());
            }
            responData.setStatus(false);
            responData.setPayload(null);
            errors.getAllErrors().forEach(error -> responData.getMessages().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(responData);
        }
        responData.setStatus(true);
        responData.setPayload(productService.saveProduct(productEntities));
        return ResponseEntity.ok(responData);
    }
    @DeleteMapping("/{productId}")// delete data by id
    public void deleteById(@PathVariable("productId")Long productId){
        productService.getDeleteById(productId);
    }
    @GetMapping// get all data
    public Iterable<ProductEntities> getAllProduct(){
        return productService.getAllProduct();
    }

    @GetMapping("/{productId}")// get data by id
    public ProductEntities findById(@PathVariable("productId")Long productId){
        return productService.findById(productId);
    }
    @PostMapping("/{productId}")// add supplier to product
    public void addSupplierToProduct(@PathVariable ("productId")
                                         @RequestBody SupplierEntities supplierEntities, Long productId){
        productService.addSupplier(supplierEntities,productId);
    }

}
