package com.web.api.model.repo;

import com.web.api.model.entities.ProductEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<ProductEntities,Long> {
}
