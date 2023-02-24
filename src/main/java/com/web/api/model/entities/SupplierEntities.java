package com.web.api.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_supplier")
public class SupplierEntities implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;
    @Column(name = "supplier_name", length = 100, nullable = false)
    private String supplierName;
    @Column(name = "supplier_address", length = 100, nullable = false)
    private String supplierAddress;
    @Column(name = "supplier_email", unique = true, length = 100, nullable = false)
    private String supplierEmail;
    //    Relationship with product
    @ManyToMany(mappedBy = "supplierProduct")
    @JsonIgnoreProperties("SupplierProduct")
    private Set<ProductEntities> productSupplier;

    //    Constructor
    public SupplierEntities(Long supplierId, String supplierName, String supplierAddress, String supplierEmail) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierEmail = supplierEmail;
    }

    public SupplierEntities() {
    }

    //    Getter Setter
    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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
//    Getter Setter Product

    public Set<ProductEntities> getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(Set<ProductEntities> productSupplier) {
        this.productSupplier = productSupplier;
    }
}
