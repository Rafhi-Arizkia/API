package com.web.api.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
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
    @Builder
    public ProductEntities(Long productId, String productName, String productDescription, Double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

}
