const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8086';

export type ServiceStatus = 'UP' | 'DEGRADED' | 'DOWN';

export interface AiServiceHealth {
  status: ServiceStatus;
  service?: string;
  version?: string;
}

export interface SystemHealthResponse {
  service: string;
  status: ServiceStatus;
  aiService: AiServiceHealth;
  version: string;
  timestamp: string;
}

export class SystemHealthError extends Error {}

export async function fetchSystemHealth(): Promise<SystemHealthResponse> {
  const response = await fetch(`${API_BASE_URL}/api/v1/system/health`);

  if (!response.ok) {
    throw new SystemHealthError(`API responded with status ${response.status}`);
  }

  return (await response.json()) as SystemHealthResponse;
}
