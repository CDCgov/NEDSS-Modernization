package com.example.save_dedupe_configuration.controller;

import com.example.save_dedupe_configuration.model.DataElement;
import com.example.save_dedupe_configuration.service.DataElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/save-data-elements/config")
public class DataElementConfigController {

    @Autowired
    private DataElementService dataElementService;

    @GetMapping
    public List<DataElement> getAllDataElements() {
        return dataElementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataElement> getDataElementById(@PathVariable Long id) {
        Optional<DataElement> dataElement = dataElementService.findById(id);
        return dataElement.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DataElement createDataElement(@RequestBody DataElement dataElement) {
        return dataElementService.save(dataElement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataElement> updateDataElement(@PathVariable Long id, @RequestBody DataElement dataElementDetails) {
        Optional<DataElement> optionalDataElement = dataElementService.findById(id);

        if (!optionalDataElement.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        DataElement dataElement = optionalDataElement.get();
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDataElement(@PathVariable Long id) {
        dataElementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
