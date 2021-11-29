package com.nurtivillage.java.nutrivillageApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import comnurtivillage.java.nutrivillageApplication.model.DeviceMetadata;

public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    List<DeviceMetadata> findByUserId(Long userId);
}
