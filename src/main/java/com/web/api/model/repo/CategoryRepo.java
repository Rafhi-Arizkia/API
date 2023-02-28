package com.web.api.model.repo;

import com.web.api.model.entities.CategoryEntities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntities, Long> {

    Page<CategoryEntities> findByCategoryNameContains(String categoryName, Pageable pageable);
}
