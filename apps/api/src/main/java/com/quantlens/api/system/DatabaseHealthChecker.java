package com.quantlens.api.system;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthChecker {

    private static final Logger log = LoggerFactory.getLogger(DatabaseHealthChecker.class);
    private static final int VALIDATION_TIMEOUT_SECONDS = 2;

    private final DataSource dataSource;

    public DatabaseHealthChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isUp() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(VALIDATION_TIMEOUT_SECONDS);
        } catch (SQLException ex) {
            log.warn("Database health check failed: {}", ex.getMessage());
            return false;
        }
    }
}
