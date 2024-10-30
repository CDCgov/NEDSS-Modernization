package com.example.save_dedupe_configuration.repository;

import com.example.save_dedupe_configuration.model.BlockingCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockingCriteriaRepository extends JpaRepository<BlockingCriteria, Long> {}
