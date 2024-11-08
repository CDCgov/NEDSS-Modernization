package com.example.save_dedupe_configuration.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "matching_criteria")
public class MatchingCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "criteria", nullable = false)
    private String criteria;

    @ManyToOne
    @JoinColumn(name = "pass_configuration_id", nullable = false)
    private PassConfiguration passConfiguration;

    @ManyToOne  // Assuming a Many-to-One relationship with Method
    @JoinColumn(name = "method_id", nullable = false) // Ensure this matches your database schema
    private Method method;  // Add this field for Method

    // Constructors
    public MatchingCriteria() {}

    // Parameterized constructor for deserialization
    @JsonCreator
    public MatchingCriteria(
            @JsonProperty("criteria") String criteria,
            @JsonProperty("passConfiguration") PassConfiguration passConfiguration,
            @JsonProperty("method") Method method) {  // Add Method to constructor
        this.criteria = criteria;
        this.passConfiguration = passConfiguration;
        this.method = method;  // Initialize Method
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public PassConfiguration getPassConfiguration() {
        return passConfiguration;
    }

    public void setPassConfiguration(PassConfiguration passConfiguration) {
        this.passConfiguration = passConfiguration;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
