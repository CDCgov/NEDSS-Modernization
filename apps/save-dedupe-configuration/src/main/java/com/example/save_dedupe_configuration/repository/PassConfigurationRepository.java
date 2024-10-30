package com.example.save_dedupe_configuration.repository;

import com.example.save_dedupe_configuration.model.PassConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassConfigurationRepository extends JpaRepository<PassConfiguration, Long> {}
