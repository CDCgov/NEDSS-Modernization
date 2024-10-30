package com.example.save_dedupe_configuration.controller;

import com.example.save_dedupe_configuration.model.DataElement;
import com.example.save_dedupe_configuration.service.DataElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Data Elements API", description = "Manage data elements for blocking and matching")
@RequestMapping("/api/configurations/data-elements")
@CrossOrigin(origins = "http://localhost:3000")
public class DataElementConfigController {

    @Autowired
    private DataElementService dataElementService;

    // Get all data elements
    @GetMapping
    public List<DataElement> getAllDataElements() {
        return dataElementService.findAll();
    }

    // Get a single data element by ID
    @GetMapping("/{id}")
    public ResponseEntity<DataElement> getDataElementById(@PathVariable Long id) {
        Optional<DataElement> dataElement = dataElementService.findById(id);
        return dataElement.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Create or update data elements
    @PostMapping
    public ResponseEntity<List<DataElement>> saveDataElement(@RequestBody Map<String, Object> payload) {
        try {
            // Log the received payload
            System.out.println("Received payload: " + payload);

            // Parse belongingnessRatio from the payload
            Integer belongingnessRatio = Integer.parseInt(payload.get("belongingnessRatio").toString());

            // Parse and cast the list of data elements from the payload
            List<Map<String, Object>> dataElementsMap = (List<Map<String, Object>>) payload.get("dataElements");

            // Convert Map to DataElement objects
            List<DataElement> dataElements = dataElementsMap.stream().map(elementMap -> {
                DataElement dataElement = new DataElement();

                // Check if the ID exists in the incoming payload for updates
                if (elementMap.containsKey("id")) {
                    Long id = Long.parseLong(elementMap.get("id").toString());
                    dataElement.setId(id); // Set ID to update existing record
                }

                dataElement.setName(elementMap.get("name").toString());
                dataElement.setLabel(elementMap.get("label").toString());
                dataElement.setCategory(elementMap.get("category").toString());
                dataElement.setActive(Boolean.parseBoolean(elementMap.get("active").toString()));
                dataElement.setM(BigDecimal.valueOf(Double.parseDouble(elementMap.get("m").toString())));
                dataElement.setU(BigDecimal.valueOf(Double.parseDouble(elementMap.get("u").toString())));
                dataElement.setThreshold(BigDecimal.valueOf(Double.parseDouble(elementMap.get("threshold").toString())));
                dataElement.setOddsRatio(BigDecimal.valueOf(Double.parseDouble(elementMap.get("oddsRatio").toString())));
                dataElement.setLogOdds(BigDecimal.valueOf(Double.parseDouble(elementMap.get("logOdds").toString())));
                dataElement.setBelongingnessRatio(belongingnessRatio); // Use the parsed belongingnessRatio
                return dataElement;
            }).collect(Collectors.toList());

            // Check for existing entries before saving
            for (DataElement dataElement : dataElements) {
                Optional<DataElement> existingElement = dataElementService.findByName(dataElement.getName());
                if (existingElement.isPresent()) {
                    // Optionally handle updates if the element already exists
                    // e.g., updating fields, or returning the existing entry
                    dataElement.setId(existingElement.get().getId()); // Set the existing ID for future updates
                }
            }

            // Save the data elements to the database
            List<DataElement> savedElements = dataElementService.saveAll(dataElements);

            return ResponseEntity.ok(savedElements);
        } catch (Exception e) {
            System.err.println("Error saving data elements: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update an existing data element by ID
    @PutMapping("/{id}")
    public ResponseEntity<DataElement> updateDataElement(@PathVariable Long id, @RequestBody DataElement dataElementDetails) {
        Optional<DataElement> optionalDataElement = dataElementService.findById(id);

        if (!optionalDataElement.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        DataElement dataElement = optionalDataElement.get();
        // Updating fields based on dataElementDetails
        dataElement.setName(dataElementDetails.getName());
        dataElement.setLabel(dataElementDetails.getLabel());
        dataElement.setCategory(dataElementDetails.getCategory());
        dataElement.setActive(dataElementDetails.getActive());
        dataElement.setM(dataElementDetails.getM());
        dataElement.setU(dataElementDetails.getU());
        dataElement.setThreshold(dataElementDetails.getThreshold());
        dataElement.setOddsRatio(dataElementDetails.getOddsRatio());
        dataElement.setLogOdds(dataElementDetails.getLogOdds());
        dataElement.setBelongingnessRatio(dataElementDetails.getBelongingnessRatio());

        DataElement updatedDataElement = dataElementService.save(dataElement);
        return ResponseEntity.ok(updatedDataElement);
    }

    // Delete a data element by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDataElement(@PathVariable Long id) {
        dataElementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // CORS Test Method (Optional)
    @GetMapping("/test-cors")
    @CrossOrigin(origins = "http://localhost:3001")
    public ResponseEntity<String> testCors() {
        return ResponseEntity.ok("CORS is working!");
    }

    // Error handling during creation (Optional)
    @PostMapping("/error-handling")
    public ResponseEntity<DataElement> createDataElementWithErrorHandling(@RequestBody DataElement dataElement) {
        try {
            DataElement savedElement = dataElementService.save(dataElement);
            return ResponseEntity.ok(savedElement);
        } catch (Exception e) {
            // Log the exception details for debugging
            System.err.println("Error saving data element: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
