package com.quantlens.api.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemHealthController {

    private final AiServiceClient aiServiceClient;
    private final String apiVersion;

    public SystemHealthController(
            AiServiceClient aiServiceClient,
            @Value("${quantlens.version}") String apiVersion) {
        this.aiServiceClient = aiServiceClient;
        this.apiVersion = apiVersion;
    }

    @GetMapping("/api/v1/system/health")
    public SystemHealthResponse getSystemHealth() {
        AiServiceHealthResponse aiHealth = aiServiceClient.fetchHealth();
        return SystemHealthResponse.of(aiHealth, apiVersion);
    }
}
