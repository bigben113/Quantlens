package com.quantlens.api.system;

import java.time.Instant;

public record SystemHealthResponse(
        String service,
        String status,
        AiServiceHealthResponse aiService,
        String version,
        Instant timestamp) {

    public static SystemHealthResponse of(AiServiceHealthResponse aiService, String apiVersion) {
        String status = "UP".equals(aiService.status()) ? "UP" : "DEGRADED";
        return new SystemHealthResponse("quantlens-api", status, aiService, apiVersion, Instant.now());
    }
}
