package com.web.api.controllers;

import com.web.api.model.dto.CategoryDTO;
import com.web.api.model.dto.ResponData;
import com.web.api.model.entities.CategoryEntities;
import com.web.api.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    //    Method untuk menambahkan data category
    @PostMapping
    public ResponseEntity<ResponData<CategoryEntities>> create(@Valid @RequestBody
                                                               CategoryDTO categoryDTO, Errors errors) {
        ResponData<CategoryEntities> responData = new ResponData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responData.getMessages().add(error.getDefaultMessage());
            }
            responData.setStatus(false);
            responData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
        }
//        Menggabungkan data dari categoryDTO dan categoryEntities meggunakan mode mapper
        CategoryEntities categoryEntities = modelMapper.map(categoryDTO, CategoryEntities.class);
        responData.setStatus(true);
        responData.setPayload(categoryService.saveCategory(categoryEntities));
        return ResponseEntity.ok(responData);
    }

    //    Method untuk update data category
    @PutMapping
    public ResponseEntity<ResponData<CategoryEntities>> update(@Valid @RequestBody
                                                               CategoryDTO categoryDTO, Errors errors) {
        ResponData<CategoryEntities> responData = new ResponData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responData.getMessages().add(error.getDefaultMessage());
            }
            responData.setStatus(false);
            responData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responData);
        }
        CategoryEntities categoryEntities = modelMapper.map(categoryDTO, CategoryEntities.class);
        responData.setStatus(true);
        responData.setPayload(categoryService.saveCategory(categoryEntities));
        return ResponseEntity.ok(responData);
    }

    //    Method untuk mencari data berdasarkan id
    @GetMapping("/{categoryId}")
    public CategoryEntities findById(@PathVariable("categoryId") Long categoryId) {
        return categoryService.findById(categoryId);
    }

    // Method untuk mengambil semua data category
    @GetMapping
    public Iterable<CategoryEntities> findAll() {
        return categoryService.showAllCategory();
    }

    // Method untuk menghapus data category berdasarkan id
    @DeleteMapping("/{categoryId}")
    public void deleteById(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteById(categoryId);
    }
}
