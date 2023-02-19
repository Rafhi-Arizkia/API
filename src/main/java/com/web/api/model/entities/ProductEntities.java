package com.web.api.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class ProductEntities implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name",length = 100)
    @NotEmpty(message = "Required Name Product")
    private String productName;
    @Column(name = "product_description",length = 150)
    private String productDescription;
    @Column(name = "product_price")
    @NotNull(message = "Required product price")
    private Double productPrice;
//    Relationship with table category
    @ManyToOne
    private CategoryEntities categoryEntities;
//    Relationship with table Supplier
    @ManyToMany
    @JoinTable(name = "tb_product_supplier",
    joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    private Set<SupplierEntities> supplierEntities;

//    Constructor
    public  ProductEntities(Long productId,String productName,String productDescription,Double productPrice){
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }
    public ProductEntities() {
    }

//    Getter Setter
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

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
//    Getter Setter Category
    public CategoryEntities getCategoryEntities() {
        return categoryEntities;
    }

    public void setCategoryEntities(CategoryEntities categoryEntities) {
        this.categoryEntities = categoryEntities;
    }
//    Getter Setter Supplier

    public Set<SupplierEntities> getSupplierEntities() {
        return supplierEntities;
    }

    public void setSupplierEntities(Set<SupplierEntities> supplierEntities) {
        this.supplierEntities = supplierEntities;
    }
}
