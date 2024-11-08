package com.example.save_dedupe_configuration.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchingCriteriaDTO {

    private Long id;
    private String criteria;  // Ensure this field exists
    private String method;    // Assuming method is also part of the DTO

    // Constructors
    @JsonCreator
    public MatchingCriteriaDTO(
            @JsonProperty("id") Long id,
            @JsonProperty("criteria") String criteria,
            @JsonProperty("method") String method) {
        this.id = id;
        this.criteria = criteria;
        this.method = method;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCriteria() {
        return criteria;  // Getter for criteria
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

