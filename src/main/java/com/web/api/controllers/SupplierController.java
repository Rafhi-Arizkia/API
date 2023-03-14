package com.web.api.controllers;

import com.web.api.dto.ResponData;
import com.web.api.dto.SearchData;
import com.web.api.dto.SupplierDTO;
import com.web.api.model.entities.SupplierEntities;
import com.web.api.service.SupplierService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    private final ModelMapper modelMapper;
    private final SupplierService supplierService;

    @Autowired  // constructor untuk injection claas SupplierService dan ModelMapper
    public SupplierController(ModelMapper modelMapper, SupplierService supplierService) {
        this.modelMapper = modelMapper;
        this.supplierService = supplierService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @PostMapping// method untuk add data supplier
    public ResponseEntity<ResponData<SupplierEntities>> saveSupplier
            (@Valid @RequestBody SupplierDTO supplierDTO, Errors errors) {
        ResponData<SupplierEntities> responData = new ResponData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responData.getMessages().add(error.getDefaultMessage());
            }
            responData.setStatus(false);
            responData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
        }
        SupplierEntities supplierEntities = modelMapper.map(supplierDTO, SupplierEntities.class);
        responData.setStatus(true);
        responData.setPayload(supplierService.saveSupplier(supplierEntities));
        return ResponseEntity.ok(responData);
    }

    @PutMapping// method untuk update data supplier
    public ResponseEntity<ResponData<SupplierEntities>> updateSupplier
            (@Valid @RequestBody SupplierDTO supplierDTO, Errors errors) {
        ResponData<SupplierEntities> responData = new ResponData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responData.getMessages().add(error.getDefaultMessage());
            }
            responData.setStatus(false);
            responData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
        }
//        Menggabungkan data dari supplierDTO dan supplierEntities meggunakan mode mapper
        SupplierEntities supplierEntities = modelMapper.map(supplierDTO, SupplierEntities.class);
        responData.setStatus(true);
        responData.setPayload(supplierService.saveSupplier(supplierEntities));
        return ResponseEntity.ok(responData);
    }

    @GetMapping // method untuk menampilkan data supplier
    public Iterable<SupplierEntities> getAllSupplier() {
        return supplierService.showAllSupplier();
    }

    @GetMapping("/{productId}") // method untuk menampilkan data supplier berdasarkan id
    public ResponseEntity<SupplierEntities> findSupplierById(@PathVariable Long productId) {
        SupplierEntities supplierEntities = supplierService.findSupplierById(productId);
        if (supplierEntities != null) {
            return ResponseEntity.ok().body(supplierEntities);
        } else {
            return ResponseEntity.notFound().build();
        }
        // Ini adalah block kode yang digunakan untuk mengambil data Supplier berdasarkan ID.
        // Jika data user ditemukan, maka akan dikembalikan sebagai respon HTTP 200 (OK)
        // beserta data user yang ditemukan.
        // Namun, jika data user tidak ditemukan, maka akan dikembalikan respon HTTP 404
        // (Not Found),
        // menandakan bahwa data tidak dapat ditemukan.
    }

    @DeleteMapping("/{productId}") // method untuk menghapus data supplier berdasarkan id
    public void deleteSupplierById(@PathVariable Long productId) {
        supplierService.deleteSupplierById(productId);
    }

//    End Point untuk derived method
    @PostMapping("/email")
    public SupplierEntities findSupplierEmail(@RequestBody SearchData searchData){
        return supplierService.findSupplierByEmail(searchData.getSearchKey());
    }
    @PostMapping("/name")
    public List<SupplierEntities> findSupplierName(@RequestBody SearchData searchData){
        return supplierService.findSupplierByName(searchData.getSearchKey());
    }

    @PostMapping("/name/prefix")
    public List<SupplierEntities> findSupplierNamePrefix(@RequestBody SearchData searchData){
        return supplierService.findSupplierNameByPrefix(searchData.getSearchKey());
    }

    @PostMapping("/nameoremail")
    public List<SupplierEntities>findSupplierNameOrEmail(@RequestBody SearchData searchData){
        return supplierService.findSupplierByNameOrEmail(searchData.getSearchKey(), searchData.getOtherKey());
    }
}
