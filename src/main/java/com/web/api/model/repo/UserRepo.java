package com.web.api.model.repo;

import com.web.api.model.entities.UserEntities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntities, Long> {
    Optional<UserEntities> findByUserEmail (String userEmail);
}
