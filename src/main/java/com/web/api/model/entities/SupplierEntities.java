package com.web.api.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_supplier")
public class SupplierEntities extends BaseEntities<String> implements Serializable {
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

}
