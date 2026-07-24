package com.quantlens.api.modelregistry;

import com.quantlens.api.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "model_registry", uniqueConstraints = @UniqueConstraint(columnNames = {"model_name", "model_version"}))
public class ModelRegistryEntry extends BaseEntity {

    @Column(name = "model_name", nullable = false)
    private String modelName;

    @Column(name = "model_version", nullable = false)
    private String version;

    @Column(name = "model_type", nullable = false)
    private String type;

    @Column(name = "status", nullable = false)
    private String status;

    protected ModelRegistryEntry() {
    }

    public ModelRegistryEntry(String modelName, String version, String type, String status) {
        this.modelName = modelName;
        this.version = version;
        this.type = type;
        this.status = status;
    }

    public String getModelName() {
        return modelName;
    }

    public String getVersion() {
        return version;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
