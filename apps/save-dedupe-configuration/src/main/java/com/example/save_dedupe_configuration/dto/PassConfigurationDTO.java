package com.example.save_dedupe_configuration.dto;

import com.example.save_dedupe_configuration.model.BlockingCriteria;
import com.example.save_dedupe_configuration.model.MatchingCriteria;
import com.example.save_dedupe_configuration.dto.*;

import java.math.BigDecimal;
import java.util.List;

public class PassConfigurationDTO {
    private String name;
    private String description;
    private boolean active;
    private BigDecimal lowerBound;
    private BigDecimal upperBound;
    private List<BlockingCriteriaDTO> blockingCriteria;
    private List<MatchingCriteriaDTO> matchingCriteria;

    @Override
    public String toString() {
        return "PassConfigurationDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", blockingCriteria=" + blockingCriteria +
                ", matchingCriteria=" + matchingCriteria +
                ", lowerBound=" + lowerBound +
                ", upperBound=" + upperBound +
                '}';
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(BigDecimal lowerBound) {
        this.lowerBound = lowerBound;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }

    public List<BlockingCriteriaDTO> getBlockingCriteria() {
        return blockingCriteria;
    }

    public void setBlockingCriteria(List<BlockingCriteriaDTO> blockingCriteria) {
        this.blockingCriteria = blockingCriteria;
    }

    public void setMatchingCriteria(List<MatchingCriteriaDTO> matchingCriteria) {
        this.matchingCriteria = matchingCriteria;
    }

    public List<MatchingCriteriaDTO> getMatchingCriteria() {
        return matchingCriteria;
    }
}
