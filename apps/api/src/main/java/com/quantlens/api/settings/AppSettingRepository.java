package com.quantlens.api.settings;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppSettingRepository extends JpaRepository<AppSetting, UUID> {
}
