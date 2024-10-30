package com.example.save_dedupe_configuration.service;

import com.example.save_dedupe_configuration.model.BlockingCriteria;
import com.example.save_dedupe_configuration.model.MatchingCriteria;
import com.example.save_dedupe_configuration.model.PassConfiguration;
import com.example.save_dedupe_configuration.repository.BlockingCriteriaRepository;
import com.example.save_dedupe_configuration.repository.MatchingCriteriaRepository;
import com.example.save_dedupe_configuration.repository.PassConfigurationRepository;
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
    public PassConfiguration savePassConfiguration(PassConfiguration passConfiguration) {
        return passConfigurationRepository.save(passConfiguration);
    }

    @Transactional
    public List<BlockingCriteria> saveBlockingCriteria(Long passConfigurationId, List<BlockingCriteria> blockingCriteriaList) {
        PassConfiguration passConfiguration = passConfigurationRepository.findById(passConfigurationId)
                .orElseThrow(() -> new IllegalArgumentException("Pass configuration not found"));

        for (BlockingCriteria criteria : blockingCriteriaList) {
            criteria.setPassConfiguration(passConfiguration);
        }
        return blockingCriteriaRepository.saveAll(blockingCriteriaList);
    }

    @Transactional
    public List<MatchingCriteria> saveMatchingCriteria(Long passConfigurationId, List<MatchingCriteria> matchingCriteriaList) {
        PassConfiguration passConfiguration = passConfigurationRepository.findById(passConfigurationId)
                .orElseThrow(() -> new IllegalArgumentException("Pass configuration not found"));

        for (MatchingCriteria criteria : matchingCriteriaList) {
            criteria.setPassConfiguration(passConfiguration);
        }
        return matchingCriteriaRepository.saveAll(matchingCriteriaList);
    }
}
