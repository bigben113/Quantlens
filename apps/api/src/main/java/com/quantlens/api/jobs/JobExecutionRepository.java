package com.quantlens.api.jobs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobExecutionRepository extends JpaRepository<JobExecution, UUID> {
}
