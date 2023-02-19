package com.web.api.model.repo;

import com.web.api.model.entities.SupplierEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepo extends JpaRepository<SupplierEntities,Long> {
}
