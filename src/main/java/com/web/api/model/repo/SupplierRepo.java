package com.web.api.model.repo;

import com.web.api.model.entities.SupplierEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepo extends JpaRepository<SupplierEntities, Long> {

//    Derived method atau ORM

//    Derived method to get supplier by name
//    Ini seperti query di SQL yang menggunakan LIKE
    List<SupplierEntities> findBySupplierNameContains(String supplierName);

//    Derived method to get supplier email
//    Ini seperti query di sql yang mencari data berdasarkan email yang hanya ada di data tersebut
//    yang artinya tidak memiliki suggestion
    public  SupplierEntities findBySupplierEmail(String supplierEmail);

//    Derived method StartWith
//    Mencari data menggunakan awalan yang diinputkan
    List<SupplierEntities> findBySupplierNameStartingWith(String prefix);

//   Derived method untuk mencari data bedasarkan email atau nama
    List<SupplierEntities> findBySupplierNameContainsOrSupplierEmailContains(String supplierName, String supplierEmail);
}
