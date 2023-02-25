package com.web.api.model.repo;

import com.web.api.model.entities.SupplierEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepo extends JpaRepository<SupplierEntities, Long> {

    //    Mennampilkan semua data supplier
    @Override
    List<SupplierEntities> findAll();
}
