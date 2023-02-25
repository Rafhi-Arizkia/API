package com.web.api.service;

import com.web.api.model.entities.CategoryEntities;
import com.web.api.model.repo.CategoryRepo;
import jakarta.transaction.TransactionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@TransactionScoped
public class CategoryService {

    private final CategoryRepo categoryRepo;


    @Autowired
    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // Method  saveCategory untuk menyimpan data category
    public CategoryEntities saveCategory(CategoryEntities categoryEntities) {
        return categoryRepo.save(categoryEntities);
    }

    // Method findById untuk menampilkan data category berdasarkan id
    public CategoryEntities findById(Long categoryId) {
        Optional<CategoryEntities> categoryEntities = categoryRepo.findById(categoryId);
        if (categoryEntities.isEmpty()) {
            throw new RuntimeException("Invalid category Id:" + categoryId);
        }
        return categoryEntities.get();
    }

    // Method menampilkan semua data category
    public Iterable<CategoryEntities> showAllCategory() {
        return categoryRepo.findAll();
    }

    // Method deleteById untuk menghapus data category berdasarkan id
    public void deleteById(Long categoryId) {
        categoryRepo.deleteById(categoryId);
    }

}
