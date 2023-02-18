package com.web.api.model.entities;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "tb_category")
public class CategoryEntities implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "category_name", length = 100, unique = true)
    private String categoryName;
//  Constructor
    public CategoryEntities(Long categoryId,String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    public CategoryEntities() {
    }

//    Getter Setter
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
