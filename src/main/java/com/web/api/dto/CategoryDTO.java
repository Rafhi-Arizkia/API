package com.web.api.dto;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDTO {

    private Long categoryId;
    @NotEmpty(message = "Category name must not be empty")
    private String categoryName;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
