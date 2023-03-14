package com.web.api.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class ProductEntities extends BaseEntities<String> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name", length = 100)
    @NotEmpty(message = "Required Name Product")
    private String productName;
    @Column(name = "product_description", length = 150)
    private String productDescription;
    @Column(name = "product_price")
    @NotNull(message = "Required product price")
    private Double productPrice;
//    Relationship with table category

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntities categoryProduct;
    //    Relationship with table Supplier
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_product_supplier",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    @JsonIgnoreProperties("productSupplier")
    private Set<SupplierEntities> supplierProduct;

    //    Constructor
    public ProductEntities(Long productId, String productName, String productDescription, Double productPrice) {
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

    public CategoryEntities getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(CategoryEntities categoryProduct) {
        this.categoryProduct = categoryProduct;
    }


    //    Getter Setter Supplier

    public Set<SupplierEntities> getSupplierProduct() {
        return supplierProduct;
    }

    public void setSupplierProduct(Set<SupplierEntities> supplierProduct) {
        this.supplierProduct = supplierProduct;
    }
}
