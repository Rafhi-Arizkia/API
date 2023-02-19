package com.web.api.model.repo;

import com.web.api.model.entities.CategoryEntities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryEntities, Long> {
}
