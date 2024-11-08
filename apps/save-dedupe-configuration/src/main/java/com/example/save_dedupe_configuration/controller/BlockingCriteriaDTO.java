package com.example.save_dedupe_configuration.controller;

import com.example.save_dedupe_configuration.dto.BlockingCriteriaDTO;
import com.example.save_dedupe_configuration.model.BlockingCriteria;
import com.example.save_dedupe_configuration.model.DataElement;
import com.example.save_dedupe_configuration.model.Method;
import com.example.save_dedupe_configuration.service.DataElementService;
import com.example.save_dedupe_configuration.service.MethodService;
import com.example.save_dedupe_configuration.repository.BlockingCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Blocking Criteria API", description = "Manage blocking criteria for deduplication")
@RestController
@RequestMapping("/api/configurations")
@CrossOrigin(origins = "http://localhost:3000")
public class BlockingCriteriaController {

    private static final Logger logger = LoggerFactory.getLogger(BlockingCriteriaController.class);

    @Autowired
    private DataElementService dataElementService;

    @Autowired
    private MethodService methodService;

    @Autowired
    private BlockingCriteriaRepository blockingCriteriaRepository;

    @Operation(summary = "Save blocking criteria", description = "Creates or updates the blocking criteria based on selected fields")
    @PostMapping("/blocking-criteria")
    public ResponseEntity<String> saveBlockingCriteria(@RequestBody List<BlockingCriteriaDTO> blockingCriteriaDtoList) {
        logger.info("Received blocking criteria: {}", blockingCriteriaDtoList);

        // Iterate through the received list of BlockingCriteriaDTO
        List<BlockingCriteria> blockingCriteriaList = blockingCriteriaDtoList.stream().map(bcDto -> {
            // Find DataElement and Method by name
            DataElement dataElement = dataElementService.findByName(bcDto.getDataElementName())
                    .orElseThrow(() -> new RuntimeException("Data element not found: " + bcDto.getDataElementName()));
            Method method = methodService.findByName(bcDto.getMethod())
                    .orElseThrow(() -> new RuntimeException("Method not found: " + bcDto.getMethod()));

            // Create or update BlockingCriteria based on provided ID (if available)
            BlockingCriteria blockingCriteria = (bcDto.getId() == null) ? new BlockingCriteria() :
                    blockingCriteriaRepository.findById(bcDto.getId())
                            .orElseThrow(() -> new RuntimeException("BlockingCriteria not found for ID: " + bcDto.getId()));

            // Set dataElement and method to the BlockingCriteria
            blockingCriteria.setDataElement(dataElement);
            blockingCriteria.setMethod(method);

            return blockingCriteriaRepository.save(blockingCriteria);
        }).collect(Collectors.toList());

        // Log the updated criteria and return success message
        logger.info("Blocking criteria saved: {}", blockingCriteriaList);
        return ResponseEntity.ok("Blocking criteria saved successfully");
    }
}
