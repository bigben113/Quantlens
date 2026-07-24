package com.quantlens.api.system;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SystemHealthController.class)
class SystemHealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AiServiceClient aiServiceClient;

    @MockitoBean
    private DatabaseHealthChecker databaseHealthChecker;

    @Test
    void returnsUpStatusAndContractWhenDatabaseAndAiServiceAreAvailable() throws Exception {
        when(aiServiceClient.fetchHealth())
                .thenReturn(AiServiceHealthResponse.up("quantlens-ai-service", "0.1.0"));
        when(databaseHealthChecker.isUp()).thenReturn(true);

        mockMvc.perform(get("/api/v1/system/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.service").value("quantlens-api"))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.database").value("UP"))
                .andExpect(jsonPath("$.aiService.status").value("UP"))
                .andExpect(jsonPath("$.aiService.service").value("quantlens-ai-service"))
                .andExpect(jsonPath("$.aiService.version").value("0.1.0"))
                .andExpect(jsonPath("$.version").value("0.1.0"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void returnsDegradedStatusWhenAiServiceIsUnavailable() throws Exception {
        when(aiServiceClient.fetchHealth()).thenReturn(AiServiceHealthResponse.down());
        when(databaseHealthChecker.isUp()).thenReturn(true);

        mockMvc.perform(get("/api/v1/system/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("quantlens-api"))
                .andExpect(jsonPath("$.status").value("DEGRADED"))
                .andExpect(jsonPath("$.database").value("UP"))
                .andExpect(jsonPath("$.aiService.status").value("DOWN"))
                .andExpect(jsonPath("$.aiService.service").doesNotExist())
                .andExpect(jsonPath("$.aiService.version").doesNotExist());
    }

    @Test
    void returnsDegradedStatusWhenDatabaseIsUnavailable() throws Exception {
        when(aiServiceClient.fetchHealth())
                .thenReturn(AiServiceHealthResponse.up("quantlens-ai-service", "0.1.0"));
        when(databaseHealthChecker.isUp()).thenReturn(false);

        mockMvc.perform(get("/api/v1/system/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("quantlens-api"))
                .andExpect(jsonPath("$.status").value("DEGRADED"))
                .andExpect(jsonPath("$.database").value("DOWN"))
                .andExpect(jsonPath("$.aiService.status").value("UP"));
    }
}
