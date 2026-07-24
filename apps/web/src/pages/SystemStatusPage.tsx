import { useQuery } from '@tanstack/react-query';
import { Alert, Descriptions, Spin, Tag, Typography } from 'antd';
import type { ServiceStatus } from '../api/systemHealth';
import { fetchSystemHealth } from '../api/systemHealth';

const { Title } = Typography;

const STATUS_COLOR: Record<ServiceStatus, string> = {
  UP: 'success',
  DEGRADED: 'warning',
  DOWN: 'error',
};

function StatusTag({ status }: { status: ServiceStatus }) {
  return <Tag color={STATUS_COLOR[status]}>{status}</Tag>;
}

export function SystemStatusPage() {
  const { data, isLoading, isError, error } = useQuery({
    queryKey: ['system-health'],
    queryFn: fetchSystemHealth,
  });

  return (
    <div style={{ maxWidth: 640, margin: '48px auto', padding: '0 16px' }}>
      <Title level={2}>System Status</Title>

      {isLoading && (
        <div role="status">
          <Spin /> Checking system status...
        </div>
      )}

      {isError && (
        <Alert
          type="error"
          message="Backend unreachable"
          description={error instanceof Error ? error.message : 'Unknown error contacting the API.'}
          showIcon
        />
      )}

      {data && (
        <>
          {data.status === 'DEGRADED' && (
            <Alert
              type="warning"
              message="System degraded"
              description="The API is reachable but the AI service is not responding."
              showIcon
              style={{ marginBottom: 16 }}
            />
          )}

          <Descriptions bordered column={1} size="middle">
            <Descriptions.Item label="Web">
              <StatusTag status="UP" />
            </Descriptions.Item>
            <Descriptions.Item label={`API (${data.service})`}>
              <StatusTag status={data.status} />
            </Descriptions.Item>
            <Descriptions.Item label={`AI Service${data.aiService.service ? ` (${data.aiService.service})` : ''}`}>
              <StatusTag status={data.aiService.status} />
            </Descriptions.Item>
            <Descriptions.Item label="API version">{data.version}</Descriptions.Item>
            {data.aiService.version && (
              <Descriptions.Item label="AI service version">{data.aiService.version}</Descriptions.Item>
            )}
            <Descriptions.Item label="Checked at">{data.timestamp}</Descriptions.Item>
          </Descriptions>
        </>
      )}
    </div>
  );
}
