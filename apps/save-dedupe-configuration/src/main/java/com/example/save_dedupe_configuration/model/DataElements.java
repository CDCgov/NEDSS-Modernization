package com.example.save_dedupe_configuration.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "data_elements")
public class DataElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Field name, e.g., 'first_name'
    @Column(name = "name", nullable = false)
    private String name;

    // Descriptive label for UI
    @Column(name = "label", nullable = false)
    private String label;

    // Category like 'Demographics', 'Location'
    @Column(name = "category", nullable = false)
    private String category;

    // Indicates if this field is active or not
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    // M probability value
    @Column(name = "m", precision = 10, scale = 5)
    private BigDecimal m;

    // U probability value
    @Column(name = "u", precision = 10, scale = 5)
    private BigDecimal u;

    // Threshold value for the data element
    @Column(name = "threshold", precision = 10, scale = 5)
    private BigDecimal threshold;

    // Odds ratio for the element
    @Column(name = "odds_ratio", precision = 10, scale = 5)
    private BigDecimal oddsRatio;

    // Log of the odds ratio
    @Column(name = "log_odds", precision = 10, scale = 5)
    private BigDecimal logOdds;

    // Belongingness Ratio
    @Column(name = "belongingness_ratio", precision = 10, scale = 5)
    private BigDecimal BelongingnessRatio;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors, Getters, Setters, PrePersist, and PreUpdate methods

    public DataElement() {
    }

    // Getters and Setters for all fields...

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
