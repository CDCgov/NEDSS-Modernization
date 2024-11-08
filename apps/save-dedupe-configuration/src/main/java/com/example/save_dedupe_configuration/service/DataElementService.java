package com.example.save_dedupe_configuration.service;

import com.example.save_dedupe_configuration.model.DataElement;
import com.example.save_dedupe_configuration.repository.DataElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataElementService {

    @Autowired
    private DataElementRepository dataElementRepository;

    // Find all DataElements
    public List<DataElement> findAll() {
        return dataElementRepository.findAll();
    }

    // Find DataElement by ID
    public Optional<DataElement> findById(Long id) {
        return dataElementRepository.findById(id);
    }

    // Check if a DataElement with the same name already exists
    public Optional<DataElement> findByName(String name) {
        return dataElementRepository.findByName(name); // Implement this method in your repository
    }

    // Save a DataElement
    public DataElement save(DataElement dataElement) {
        // Check if the ID is set (non-null)
        if (dataElement.getId() != null) {
            // Existing DataElement; perform an update
            return dataElementRepository.save(dataElement);
        } else {
            // Check if DataElement with the same name already exists
            Optional<DataElement> existingElement = findByName(dataElement.getName());
            if (existingElement.isPresent()) {
                // Optionally, update the existing entry or return it
                return existingElement.get(); // Return existing DataElement instead of saving a new one
            }
            // New DataElement; perform an insert
            return dataElementRepository.save(dataElement);
        }
    }
    // Save a list of DataElements
    public List<DataElement> saveAll(List<DataElement> dataElements) {
        return dataElementRepository.saveAll(dataElements);
    }

    // Delete DataElement by ID
    public void deleteById(Long id) {
        dataElementRepository.deleteById(id);
    }
}