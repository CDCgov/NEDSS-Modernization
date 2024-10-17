package com.example.save_dedupe_configuration.repository;

import com.example.mybackend.model.DataElement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataElementRepository extends JpaRepository<DataElement, Long> {
    // Additional query methods can be defined here
}
