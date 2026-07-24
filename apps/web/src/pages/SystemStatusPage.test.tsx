import { render, screen } from '@testing-library/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { afterEach, describe, expect, it, vi } from 'vitest';
import { SystemStatusPage } from './SystemStatusPage';
import * as systemHealthApi from '../api/systemHealth';
import type { SystemHealthResponse } from '../api/systemHealth';

function renderWithClient() {
  const queryClient = new QueryClient({
    defaultOptions: { queries: { retry: false } },
  });

  return render(
    <QueryClientProvider client={queryClient}>
      <SystemStatusPage />
    </QueryClientProvider>,
  );
}

const upResponse: SystemHealthResponse = {
  service: 'quantlens-api',
  status: 'UP',
  aiService: { status: 'UP', service: 'quantlens-ai-service', version: '0.1.0' },
  version: '0.1.0',
  timestamp: '2026-01-01T00:00:00Z',
};

const degradedResponse: SystemHealthResponse = {
  service: 'quantlens-api',
  status: 'DEGRADED',
  aiService: { status: 'DOWN' },
  version: '0.1.0',
  timestamp: '2026-01-01T00:00:00Z',
};

afterEach(() => {
  vi.restoreAllMocks();
});

describe('SystemStatusPage', () => {
  it('shows a loading state before the health check resolves', () => {
    vi.spyOn(systemHealthApi, 'fetchSystemHealth').mockReturnValue(new Promise(() => {}));

    renderWithClient();

    expect(screen.getByRole('status')).toBeInTheDocument();
  });

  it('renders web, API, and AI statuses when everything is UP', async () => {
    vi.spyOn(systemHealthApi, 'fetchSystemHealth').mockResolvedValue(upResponse);

    renderWithClient();

    expect(await screen.findByText(/quantlens-api/)).toBeInTheDocument();
    expect(screen.getAllByText('UP')).toHaveLength(3);
  });

  it('renders a degraded warning when the AI service is down', async () => {
    vi.spyOn(systemHealthApi, 'fetchSystemHealth').mockResolvedValue(degradedResponse);

    renderWithClient();

    expect(await screen.findByText('System degraded')).toBeInTheDocument();
    expect(screen.getByText('DEGRADED')).toBeInTheDocument();
    expect(screen.getByText('DOWN')).toBeInTheDocument();
  });

  it('renders an error state when the API is unreachable', async () => {
    vi.spyOn(systemHealthApi, 'fetchSystemHealth').mockRejectedValue(new Error('Failed to fetch'));

    renderWithClient();

    expect(await screen.findByText('Backend unreachable')).toBeInTheDocument();
  });
});
