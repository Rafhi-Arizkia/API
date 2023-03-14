package com.web.api.service;

import com.web.api.model.entities.SupplierEntities;
import com.web.api.model.repo.SupplierRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
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
        if (supplierEntities.getSupplierId() != null) {
            SupplierEntities supplierEntities1 = supplierRepo.findById(supplierEntities.getSupplierId())
                    .orElseThrow(() -> new NotFoundException(
                            "" + "{\"error\":\"Not found product with Id\"" + supplierEntities.getSupplierId() + "\"}")
                    );
            supplierEntities1.setSupplierName(supplierEntities.getSupplierName());
            supplierEntities1.setSupplierEmail(supplierEntities.getSupplierName());
            supplierEntities1.setSupplierAddress(supplierEntities.getSupplierName());
            supplierEntities1.setProductSupplier(supplierEntities.getProductSupplier());

            supplierEntities1 = supplierEntities;
        }
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

    //    Ini menggunakan derived method
    public SupplierEntities findSupplierByEmail(String supplierEmail) {
        return supplierRepo.findBySupplierEmail(supplierEmail);
    }

    public List<SupplierEntities> findSupplierByName(String supplierName) {
        return supplierRepo.findBySupplierNameContains(supplierName);
    }

    public List<SupplierEntities> findSupplierNameByPrefix(String prefix) {
        return supplierRepo.findBySupplierNameStartingWith(prefix);
    }

    public List<SupplierEntities> findSupplierByNameOrEmail(String supplierName, String supplierEmail) {
        return supplierRepo.findBySupplierNameContainsOrSupplierEmailContains(supplierName, supplierEmail);
    }
}
