package com.web.api.service;

import com.web.api.model.entities.SupplierEntities;
import com.web.api.model.repo.SupplierRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class SupplierService {

    private final SupplierRepo supplierRepo;

    @Autowired
    public SupplierService(SupplierRepo supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    //    Method untuk menambahkan data supplier
    public SupplierEntities saveSupplier(SupplierEntities supplierEntities) {
        return supplierRepo.save(supplierEntities);
    }

    //    Method untuk mennampilkan semua data supplier
    public Iterable<SupplierEntities> showAllSupplier() {
        return supplierRepo.findAll();
    }

    //    Method untuk menampilkan data supplier berdasarkan id
    public SupplierEntities findSupplierById(Long supplierId) {
        Optional<SupplierEntities> supplierEntities = supplierRepo.findById(supplierId);
        if (supplierEntities.isEmpty()) {
            throw new RuntimeException("Invalid supplier Id:" + supplierId);
        }
        return supplierEntities.get();
    }

    public void deleteSupplierById(Long supplierId) {
        supplierRepo.deleteById(supplierId);
    }


}
