package com.web.api.dto;

import com.web.api.model.entities.ProductEntities;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

// class ini sama halnya dengan class SupplierEntities
// tetapi di class ini  hanya mengambil data yang dibutuhkan saja
public class SupplierDTO {

    @NotEmpty(message = "Supplier name is required")
    private String supplierName;
    @NotEmpty(message = "Supplier address is required")
    private String supplierAddress;
    @NotEmpty(message = "Supplier email is required")
    @Email(message = "Supplier email is invalid")
    private String supplierEmail;
    private Set<ProductEntities> supplierProduct = new HashSet<>();

    public SupplierDTO(String supplierName, String supplierAddress, String supplierEmail) {
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierEmail = supplierEmail;
    }

    public SupplierDTO() {
    }


    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public Set<ProductEntities> getSupplierProduct() {
        return supplierProduct;
    }

    public void setSupplierProduct(Set<ProductEntities> supplierProduct) {
        this.supplierProduct = supplierProduct;
    }
}
