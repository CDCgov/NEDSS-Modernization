package com.example.save_dedupe_configuration.controller;

import com.example.save_dedupe_configuration.model.DataElement;
import com.example.save_dedupe_configuration.service.DataElementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/save-data-elements")
public class DataElementController {

    private final DataElementService dataElementService;

    public DataElementController(DataElementService dataElementService) {
        this.dataElementService = dataElementService;
    }

    @GetMapping
    public List<DataElement> getAllDataElements() {
        return dataElementService.findAll();  // Use findAll() from DataElementService
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataElement> getDataElementById(@PathVariable Long id) {
        Optional<DataElement> dataElement = dataElementService.findById(id);  // Use findById() from DataElementService
        return dataElement.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DataElement createDataElement(@RequestBody DataElement dataElement) {
        return dataElementService.save(dataElement);  // Use save() from DataElementService
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataElement> updateDataElement(@PathVariable Long id, @RequestBody DataElement dataElementDetails) {
        Optional<DataElement> optionalDataElement = dataElementService.findById(id);  // Use findById() from DataElementService

        if (!optionalDataElement.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Update logic (manual fields update can be added here)
        DataElement existingDataElement = optionalDataElement.get();
        // Set fields of existingDataElement based on dataElementDetails

        DataElement updatedDataElement = dataElementService.save(existingDataElement);  // Use save() for update
        return ResponseEntity.ok(updatedDataElement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDataElement(@PathVariable Long id) {
        dataElementService.deleteById(id);  // Use deleteById() from DataElementService
        return ResponseEntity.noContent().build();
    }
}
