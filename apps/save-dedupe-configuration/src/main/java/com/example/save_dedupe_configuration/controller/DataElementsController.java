package com.example.save_dedupe_configuration.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/save-data-elements")
public class DataElementsController {

    private final DataElementsService dataElementsService;

    public DataElementsController(DataElementsService dataElementsService) {
        this.dataElementsService = dataElementsService;
    }

    @GetMapping
    public List<DataElement> getAllDataElements() {
        return dataElementsService.getAllDataElements();
    }

    @PostMapping
    public DataElement createDataElement(@RequestBody DataElement dataElement) {
        return dataElementsService.createDataElement(dataElement);
    }

    // Other CRUD methods...
}
