from fastapi import FastAPI

from app.config import settings
from app.schemas import HealthResponse

app = FastAPI(title="QuantLens AI Service", version=settings.version)


@app.get("/health", response_model=HealthResponse)
def get_health() -> HealthResponse:
    return HealthResponse(version=settings.version)
