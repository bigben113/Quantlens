from typing import Literal

from pydantic import BaseModel


class HealthResponse(BaseModel):
    service: Literal["quantlens-ai-service"] = "quantlens-ai-service"
    status: Literal["UP"] = "UP"
    version: str
