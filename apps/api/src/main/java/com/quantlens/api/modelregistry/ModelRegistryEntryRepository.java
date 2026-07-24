package com.quantlens.api.modelregistry;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRegistryEntryRepository extends JpaRepository<ModelRegistryEntry, UUID> {
}
