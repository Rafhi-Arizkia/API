package com.web.api.utils;

import com.web.api.model.entities.UserEntities;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        UserEntities userEntities = (UserEntities) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return Optional.of(userEntities.getUserEmail());
    }
}
