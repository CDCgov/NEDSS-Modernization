package com.example.save_dedupe_configuration.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;


@Entity
@Table(name = "data_elements")
public class DataElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "m", precision = 10, scale = 5)
    private BigDecimal m;

    @Column(name = "u", precision = 10, scale = 5)
    private BigDecimal u;

    @Column(name = "threshold", precision = 10, scale = 5)
    private BigDecimal threshold;

    @Column(name = "odds_ratio", precision = 10, scale = 5)
    private BigDecimal oddsRatio;

    @Column(name = "log_odds", precision = 10, scale = 5)
    private BigDecimal logOdds;

    @Column(name = "belongingness_ratio", precision = 10, scale = 5)
    private BigDecimal belongingnessRatio;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public DataElement() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getM() {
        return m;
    }

    public void setM(BigDecimal m) {
        this.m = m;
    }

    public BigDecimal getU() {
        return u;
    }

    public void setU(BigDecimal u) {
        this.u = u;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    public BigDecimal getOddsRatio() {
        return oddsRatio;
    }

    public void setOddsRatio(BigDecimal oddsRatio) {
        this.oddsRatio = oddsRatio;
    }

    public BigDecimal getLogOdds() {
        return logOdds;
    }

    public void setLogOdds(BigDecimal logOdds) {
        this.logOdds = logOdds;
    }

    public BigDecimal getBelongingnessRatio() { // Getter for belongingnessRatio
        return belongingnessRatio;
    }

    public void setBelongingnessRatio(BigDecimal belongingnessRatio) { // Setter for belongingnessRatio
        this.belongingnessRatio = belongingnessRatio;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

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
