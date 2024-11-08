package com.example.save_dedupe_configuration.repository;

import com.example.save_dedupe_configuration.model.Method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MethodRepository extends CrudRepository<Method, Long> {
    Optional<Method> findByName(String name);
}

