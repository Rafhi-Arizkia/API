package com.web.api.service;

import com.web.api.model.entities.CategoryEntities;
import com.web.api.model.repo.CategoryRepo;
import jakarta.transaction.TransactionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

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
        // untuk melakukan update
        if (categoryEntities.getCategoryId() != null) {
            CategoryEntities finalCategoryEntities = categoryEntities;
            CategoryEntities currentCategory = categoryRepo.findById(categoryEntities.getCategoryId())
                    .orElseThrow(()
                            -> new NotFoundException
                            ("{\"error\":\"Not found category with Id\"" + finalCategoryEntities.getCategoryId() + "\"}"));
            currentCategory.setCategoryName(categoryEntities.getCategoryName());
            categoryEntities = currentCategory;
        }
        return categoryRepo.save(categoryEntities);
    }

    // Method findById untuk menampilkan data category berdasarkan id
    public CategoryEntities findById(Long categoryId) {
        Optional<CategoryEntities> categoryEntities = categoryRepo.findById(categoryId);
        if (categoryEntities.isEmpty()) {
            throw new NotFoundException("Invalid category Id:" + categoryId);
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

    //    Paging and Sorting
    public Iterable<CategoryEntities> findCategoryByPage(String categoryName, Pageable pageable) {
        return categoryRepo.findByCategoryNameContains(categoryName, pageable);
    }

    //    Untuk menyimpan data category dalam jumlah banyak
    public Iterable<CategoryEntities> saveAllCategory(Iterable<CategoryEntities> categoryEntities) {
        return categoryRepo.saveAll(categoryEntities);
    }

}
