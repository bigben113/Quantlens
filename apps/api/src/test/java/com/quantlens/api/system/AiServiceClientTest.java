package com.quantlens.api.system;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class AiServiceClientTest {

    private HttpServer server;

    @AfterEach
    void stopServer() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void returnsUpResponseWhenAiServiceIsReachable() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/health", exchange -> {
            byte[] body = """
                    {"service":"quantlens-ai-service","status":"UP","version":"0.1.0"}
                    """.strip().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body);
            }
        });
        server.start();

        var properties = new AiServiceProperties("http://localhost:" + server.getAddress().getPort(), 2000);
        var client = new AiServiceClient(properties);

        AiServiceHealthResponse response = client.fetchHealth();

        assertThat(response.status()).isEqualTo("UP");
        assertThat(response.service()).isEqualTo("quantlens-ai-service");
        assertThat(response.version()).isEqualTo("0.1.0");
    }

    @Test
    void returnsDownResponseWhenAiServiceIsUnreachable() {
        var properties = new AiServiceProperties("http://localhost:1", 500);
        var client = new AiServiceClient(properties);

        AiServiceHealthResponse response = client.fetchHealth();

        assertThat(response.status()).isEqualTo("DOWN");
        assertThat(response.service()).isNull();
        assertThat(response.version()).isNull();
    }
}
