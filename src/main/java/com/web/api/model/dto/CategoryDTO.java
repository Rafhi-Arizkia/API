package com.web.api.model.dto;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDTO {

    @NotEmpty(message = "Category name must not be empty")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
