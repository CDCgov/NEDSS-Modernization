package com.example.save_dedupe_configuration.service;

import com.example.save_dedupe_configuration.model.DataElement;
import com.example.save_dedupe_configuration.repository.DataElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // Save a DataElement
    public DataElement save(DataElement dataElement) {
        return dataElementRepository.save(dataElement);
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
