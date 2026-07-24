package com.quantlens.api.system;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@EnableConfigurationProperties(AiServiceProperties.class)
public class AiServiceClient {

    private static final Logger log = LoggerFactory.getLogger(AiServiceClient.class);

    private final RestClient restClient;

    public AiServiceClient(AiServiceProperties properties) {
        Duration timeout = Duration.ofMillis(properties.timeoutMs());
        var requestFactory = ClientHttpRequestFactoryBuilder.detect().build(
                ClientHttpRequestFactorySettings.defaults()
                        .withConnectTimeout(timeout)
                        .withReadTimeout(timeout));

        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
                .requestFactory(requestFactory)
                .build();
    }

    public AiServiceHealthResponse fetchHealth() {
        try {
            AiServiceHealthResponse response = restClient.get()
                    .uri("/health")
                    .retrieve()
                    .body(AiServiceHealthResponse.class);
            return response != null ? response : AiServiceHealthResponse.down();
        } catch (RestClientException ex) {
            log.warn("AI service health check failed: {}", ex.getMessage());
            return AiServiceHealthResponse.down();
        }
    }
}
