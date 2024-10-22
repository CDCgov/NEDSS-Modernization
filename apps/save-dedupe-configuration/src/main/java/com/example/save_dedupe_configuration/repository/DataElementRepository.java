package com.example.save_dedupe_configuration.repository;

import com.example.save_dedupe_configuration.model.DataElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataElementRepository extends JpaRepository<DataElement, Long> {
    Optional<DataElement> findByName(String name);
}

