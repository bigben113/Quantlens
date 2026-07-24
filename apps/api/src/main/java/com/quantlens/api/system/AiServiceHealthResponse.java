package com.quantlens.api.system;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AiServiceHealthResponse(String status, String service, String version) {

    public static AiServiceHealthResponse up(String service, String version) {
        return new AiServiceHealthResponse("UP", service, version);
    }

    public static AiServiceHealthResponse down() {
        return new AiServiceHealthResponse("DOWN", null, null);
    }
}
