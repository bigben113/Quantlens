from fastapi.testclient import TestClient

from app.main import app

client = TestClient(app)


def test_health_returns_up_status() -> None:
    response = client.get("/health")

    assert response.status_code == 200
    body = response.json()
    assert body == {
        "service": "quantlens-ai-service",
        "status": "UP",
        "version": "0.1.0",
    }


def test_health_response_is_deterministic() -> None:
    first = client.get("/health").json()
    second = client.get("/health").json()

    assert first == second
