package com.web.api.utils;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseActuator implements HealthIndicator {
    @Override
    public Health health() {
        if (isDatabaseHealthGood()){
            return Health.up().withDetail("Database Server", "Database is running" ).build();
        }
        return Health
                .down()
                .withDetail("Database Server", "Database is not available")
                .build();
    }

    public boolean isDatabaseHealthGood(){
        return true;
    }
}
