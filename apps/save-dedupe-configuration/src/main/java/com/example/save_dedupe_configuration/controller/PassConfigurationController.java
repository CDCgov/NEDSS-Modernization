package com.example.save_dedupe_configuration.controller;

import com.example.save_dedupe_configuration.dto.PassConfigurationDTO;
import com.example.save_dedupe_configuration.dto.BlockingCriteriaDTO;
import com.example.save_dedupe_configuration.dto.MatchingCriteriaDTO;
import com.example.save_dedupe_configuration.model.*;
import com.example.save_dedupe_configuration.service.DataElementService;
import com.example.save_dedupe_configuration.service.MethodService;
import com.example.save_dedupe_configuration.service.PassConfigurationService;
import com.example.save_dedupe_configuration.repository.BlockingCriteriaRepository;
import com.example.save_dedupe_configuration.repository.MatchingCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Pass Configuration API", description = "Manage pass configurations, blocking criteria, and matching criteria")
@RestController
@RequestMapping("/api/configurations")
@CrossOrigin(origins = "http://localhost:3000")
public class PassConfigurationController {

    private static final Logger logger = LoggerFactory.getLogger(PassConfigurationController.class);

    @Autowired
    private MethodService methodService;

    @Autowired
    private PassConfigurationService passConfigurationService;

    @Autowired
    private BlockingCriteriaRepository blockingCriteriaRepository;

    @Autowired
    private MatchingCriteriaRepository matchingCriteriaRepository;

    @Autowired
    private DataElementService dataElementService;

    @Operation(summary = "Save a new pass configuration", description = "Creates or updates a pass configuration")
    @PostMapping("/save-pass")
    public ResponseEntity<String> savePassConfiguration(@RequestBody PassConfigurationDTO passConfigDto) {
        logger.info("Received PassConfigurationDTO: {}", passConfigDto);
        logger.info("Blocking Criteria IDs: {}",
                passConfigDto.getBlockingCriteria().stream().map(BlockingCriteriaDTO::getId).collect(Collectors.toList()));
        logger.info("Matching Criteria IDs: {}",
                passConfigDto.getMatchingCriteria().stream().map(MatchingCriteriaDTO::getId).collect(Collectors.toList()));

        PassConfiguration passConfiguration = new PassConfiguration();
        passConfiguration.setName(passConfigDto.getName());
        passConfiguration.setDescription(passConfigDto.getDescription());
        passConfiguration.setActive(passConfigDto.getActive());
        passConfiguration.setLowerBound(passConfigDto.getLowerBound());
        passConfiguration.setUpperBound(passConfigDto.getUpperBound());

        // Save blocking criteria
        List<BlockingCriteria> blockingCriteriaList = passConfigDto.getBlockingCriteria().stream().map(bc -> {
            DataElement dataElement = dataElementService.findByName(bc.getDataElementName())
                    .orElseThrow(() -> new RuntimeException("Data element not found: " + bc.getDataElementName()));
            Method method = methodService.findByName(bc.getMethod())
                    .orElseThrow(() -> new RuntimeException("Method not found: " + bc.getMethod()));
            BlockingCriteria blockingCriteria = (bc.getId() == null) ? new BlockingCriteria(dataElement, passConfiguration, method) :
                    blockingCriteriaRepository.findById(bc.getId())
                            .orElseThrow(() -> new RuntimeException("BlockingCriteria not found for ID: " + bc.getId()));

            blockingCriteria.setDataElement(dataElement);
            blockingCriteria.setMethod(method);

            return blockingCriteriaRepository.save(blockingCriteria);
        }).collect(Collectors.toList());

        // Save matching criteria
        List<MatchingCriteria> matchingCriteriaList = passConfigDto.getMatchingCriteria().stream().map(mc -> {
            MatchingCriteria matchingCriteria = (mc.getId() == null) ? new MatchingCriteria() :
                    matchingCriteriaRepository.findById(mc.getId())
                            .orElseThrow(() -> new RuntimeException("MatchingCriteria not found for ID: " + mc.getId()));

            // Set criteria and method for the matching criteria
            matchingCriteria.setCriteria(mc.getCriteria());
            Method method = methodService.findByName(mc.getMethod())
                    .orElseThrow(() -> new RuntimeException("Method not found: " + mc.getMethod()));
            matchingCriteria.setMethod(method);

            return matchingCriteriaRepository.save(matchingCriteria);
        }).collect(Collectors.toList());

        // Save pass configuration with all criteria
        passConfigurationService.savePassConfiguration(passConfiguration, blockingCriteriaList, matchingCriteriaList);

        // Return success message
        return ResponseEntity.ok("Pass configuration saved successfully");
    }
}
