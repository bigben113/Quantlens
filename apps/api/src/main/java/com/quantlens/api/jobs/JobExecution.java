package com.quantlens.api.jobs;

import java.time.Instant;

import com.quantlens.api.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_execution")
public class JobExecution extends BaseEntity {

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "message")
    private String message;

    protected JobExecution() {
    }

    public JobExecution(String jobName, Instant startedAt, String status) {
        this.jobName = jobName;
        this.startedAt = startedAt;
        this.status = status;
    }

    public String getJobName() {
        return jobName;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
