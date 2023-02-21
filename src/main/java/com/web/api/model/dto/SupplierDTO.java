package com.web.api.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

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
}
