package com.example.save_dedupe_configuration.service;

import com.example.save_dedupe_configuration.model.PassConfiguration;
import com.example.save_dedupe_configuration.model.BlockingCriteria;
import com.example.save_dedupe_configuration.model.MatchingCriteria;
import com.example.save_dedupe_configuration.repository.PassConfigurationRepository;
import com.example.save_dedupe_configuration.repository.BlockingCriteriaRepository;
import com.example.save_dedupe_configuration.repository.MatchingCriteriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassConfigurationService {

    @Autowired
    private PassConfigurationRepository passConfigurationRepository;

    @Autowired
    private BlockingCriteriaRepository blockingCriteriaRepository;

    @Autowired
    private MatchingCriteriaRepository matchingCriteriaRepository;

    @Transactional
    public PassConfiguration savePassConfiguration(PassConfiguration passConfiguration, List<BlockingCriteria> blockingCriteria, List<MatchingCriteria> matchingCriteria) {

        // Save pass configuration
        PassConfiguration savedConfig = passConfigurationRepository.save(passConfiguration);

        // Associate blocking and matching criteria with the saved configuration
        blockingCriteria.forEach(criteria -> {
            criteria.setPassConfiguration(savedConfig);
        });

        matchingCriteria.forEach(criteria -> {
            criteria.setPassConfiguration(savedConfig);
        });

        // Save all criteria in batches
        blockingCriteriaRepository.saveAll(blockingCriteria);
        matchingCriteriaRepository.saveAll(matchingCriteria);

        return savedConfig;
    }

}
