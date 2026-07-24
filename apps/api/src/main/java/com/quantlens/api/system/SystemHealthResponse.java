package com.quantlens.api.system;

import java.time.Instant;

public record SystemHealthResponse(
        String service,
        String status,
        String database,
        AiServiceHealthResponse aiService,
        String version,
        Instant timestamp) {

    public static SystemHealthResponse of(boolean databaseUp, AiServiceHealthResponse aiService, String apiVersion) {
        boolean aiUp = "UP".equals(aiService.status());
        String status = (databaseUp && aiUp) ? "UP" : "DEGRADED";
        String database = databaseUp ? "UP" : "DOWN";
        return new SystemHealthResponse("quantlens-api", status, database, aiService, apiVersion, Instant.now());
    }
}
