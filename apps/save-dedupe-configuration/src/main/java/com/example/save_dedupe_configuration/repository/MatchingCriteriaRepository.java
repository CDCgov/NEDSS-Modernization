package com.example.save_dedupe_configuration.repository;

import com.example.save_dedupe_configuration.model.MatchingCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingCriteriaRepository extends JpaRepository<MatchingCriteria, Long> {}
