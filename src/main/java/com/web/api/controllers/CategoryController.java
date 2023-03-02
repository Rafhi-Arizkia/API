package com.web.api.controllers;

import com.web.api.dto.CategoryDTO;
import com.web.api.dto.ResponData;
import com.web.api.dto.SearchData;
import com.web.api.model.entities.CategoryEntities;
import com.web.api.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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

    //    Method paging and sorting
    @PostMapping("/search/{size}/{pages}")
    public Iterable<CategoryEntities> findCategoryByPage(@RequestBody SearchData searchData,
                                                         @PathVariable("size") int size, @PathVariable("pages")
                                                         int pages) {
        Pageable pageable = PageRequest.of(pages, size);
        return categoryService.findCategoryByPage(searchData.getSearchKey(), pageable);
    }

    @PostMapping("/search/{size}/{pages}/{sort}")
    public Iterable<CategoryEntities> findCategoryByPage2(@RequestBody SearchData searchData,
                                                          @PathVariable("size") int size, @PathVariable("pages")
                                                          int pages,
                                                          @PathVariable("sort") String sort) {
        Pageable pageable = PageRequest.of(pages, size, Sort.by("categoryId"));
//        Jika data diatas menghasilkan ascending maka akan bisa diubah dengan descending
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(pages, size, Sort.by("categoryId").descending());
        }
        return categoryService.findCategoryByPage(searchData.getSearchKey(), pageable);
    }
    @PostMapping("/all")
    public ResponseEntity<ResponData<Iterable<CategoryEntities>>> createAllCategory(
            @RequestBody CategoryEntities categoryEntities){
        ResponData<Iterable<CategoryEntities>> responData = new ResponData<>();
        responData.setPayload(categoryService.saveAllCategory(Arrays.asList(categoryEntities)));
        responData.setStatus(true);
        return ResponseEntity.ok(responData);
    }
}

