package com.web.api.model.repo;

import com.web.api.model.entities.ProductEntities;
import com.web.api.model.entities.SupplierEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntities,Long> {
    @Query("SELECT p.supplierEntities FROM ProductEntities p where p.productId =  :productId")
    Set<SupplierEntities>findProductEntitiesByProductId(@Param("productId")Long productId);
}
