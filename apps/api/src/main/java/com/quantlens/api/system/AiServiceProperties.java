package com.quantlens.api.system;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quantlens.ai-service")
public record AiServiceProperties(String baseUrl, int timeoutMs) {
}
