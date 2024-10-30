package com.example.save_dedupe_configuration.controller;

import com.example.save_dedupe_configuration.model.DataElement;
import com.example.save_dedupe_configuration.service.DataElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.example.save_dedupe_configuration.model.PassConfiguration;
import com.example.save_dedupe_configuration.model.BlockingCriteria;
import com.example.save_dedupe_configuration.model.MatchingCriteria;
import com.example.save_dedupe_configuration.service.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pass Configuration API", description = "Manage pass configurations, blocking criteria, and matching criteria")
@RestController
@RequestMapping("/api/configurations")
@CrossOrigin(origins = "http://localhost:3000")
public class PassConfigurationController {
    @Autowired
    private PassConfigurationService passConfigurationService;

    // Endpoint to create or save a Pass Configuration
    @Operation(summary = "Save a new pass configuration", description = "Creates or updates a pass configuration")
    @PostMapping("/save-pass")
    public ResponseEntity<PassConfiguration> savePassConfiguration(@RequestBody PassConfiguration passConfiguration) {
        PassConfiguration savedConfig = passConfigurationService.savePassConfiguration(passConfiguration);
        return ResponseEntity.ok(savedConfig);
    }

    // Endpoint to add Blocking Criteria to an existing Pass Configuration
    @Operation(summary = "Save blocking criteria", description = "Adds blocking criteria to a specific pass configuration")
    @PostMapping("/save-blocking-criteria")
    public ResponseEntity<List<BlockingCriteria>> saveBlockingCriteria(
            @RequestParam Long passConfigurationId,
            @RequestBody List<BlockingCriteria> blockingCriteriaList) {
        List<BlockingCriteria> savedCriteria = passConfigurationService.saveBlockingCriteria(passConfigurationId, blockingCriteriaList);
        return ResponseEntity.ok(savedCriteria);
    }

    // Endpoint to add Matching Criteria to an existing Pass Configuration
    @Operation(summary = "Save matching criteria", description = "Adds matching criteria to a specific pass configuration")
    @PostMapping("/save-matching-criteria")
    public ResponseEntity<List<MatchingCriteria>> saveMatchingCriteria(
            @Parameter(description = "ID of the pass configuration to add matching criteria to") @RequestParam Long passConfigurationId,
            @RequestBody List<MatchingCriteria> matchingCriteriaList) {
        List<MatchingCriteria> savedCriteria = passConfigurationService.saveMatchingCriteria(passConfigurationId, matchingCriteriaList);
        return ResponseEntity.ok(savedCriteria);
    }
}