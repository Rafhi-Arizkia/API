package com.web.api.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductSupplierDTO {

    private Long productId;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private Set<SupplierDTO> supplierProduct = new HashSet<>();

    public ProductSupplierDTO(Long productId, String productName, String productDescription, Double productPrice, Set<SupplierDTO> supplierProduct) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.supplierProduct = supplierProduct;
    }

    public ProductSupplierDTO(Long productId, String productName, String productDescription, Double productPrice, List<SupplierDTO> supplierDTOs) {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Set<SupplierDTO> getSupplierProduct() {
        return supplierProduct;
    }

    public void setSupplierProduct(Set<SupplierDTO> supplierProduct) {
        this.supplierProduct = supplierProduct;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
