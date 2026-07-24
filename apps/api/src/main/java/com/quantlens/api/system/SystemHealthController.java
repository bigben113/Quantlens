package com.quantlens.api.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemHealthController {

    private final AiServiceClient aiServiceClient;
    private final DatabaseHealthChecker databaseHealthChecker;
    private final String apiVersion;

    public SystemHealthController(
            AiServiceClient aiServiceClient,
            DatabaseHealthChecker databaseHealthChecker,
            @Value("${quantlens.version}") String apiVersion) {
        this.aiServiceClient = aiServiceClient;
        this.databaseHealthChecker = databaseHealthChecker;
        this.apiVersion = apiVersion;
    }

    @GetMapping("/api/v1/system/health")
    public SystemHealthResponse getSystemHealth() {
        AiServiceHealthResponse aiHealth = aiServiceClient.fetchHealth();
        boolean databaseUp = databaseHealthChecker.isUp();
        return SystemHealthResponse.of(databaseUp, aiHealth, apiVersion);
    }
}
