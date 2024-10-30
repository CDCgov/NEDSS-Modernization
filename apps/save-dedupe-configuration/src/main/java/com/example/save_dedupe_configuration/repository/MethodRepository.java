package com.example.save_dedupe_configuration.repository;

import com.example.save_dedupe_configuration.model.Method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodRepository extends JpaRepository<Method, Long> {
}
