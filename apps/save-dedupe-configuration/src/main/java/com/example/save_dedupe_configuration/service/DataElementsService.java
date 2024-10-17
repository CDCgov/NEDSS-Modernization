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

    public List<DataElement> findAll() {
        return dataElementRepository.findAll();
    }

    public Optional<DataElement> findById(Long id) {
        return dataElementRepository.findById(id);
    }

    public DataElement save(DataElement dataElement) {
        return dataElementRepository.save(dataElement);
    }

    public List<DataElement> saveAll(List<DataElement> dataElements) {
        return dataElementRepository.saveAll(dataElements);
    }

    public void deleteById(Long id) {
        dataElementRepository.deleteById(id);
    }
}
