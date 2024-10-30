package com.example.save_dedupe_configuration.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pass_configurations")
public class PassConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "lower_bound", precision = 10, scale = 5)
    private BigDecimal lowerBound;

    @Column(name = "upper_bound", precision = 10, scale = 5)
    private BigDecimal upperBound;

    @Column(name = "total_log_odds", precision = 10, scale = 5)
    private BigDecimal totalLogOdds;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // One-to-Many relationship with BlockingCriteria and MatchingCriteria
    @OneToMany(mappedBy = "passConfiguration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlockingCriteria> blockingCriteria;

    @OneToMany(mappedBy = "passConfiguration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingCriteria> matchingCriteria;

    // Constructors
    public PassConfiguration() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
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

    public BigDecimal getTotalLogOdds() {
        return totalLogOdds;
    }

    public void setTotalLogOdds(BigDecimal totalLogOdds) {
        this.totalLogOdds = totalLogOdds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<BlockingCriteria> getBlockingCriteria() {
        return blockingCriteria;
    }

    public void setBlockingCriteria(List<BlockingCriteria> blockingCriteria) {
        this.blockingCriteria = blockingCriteria;
    }

    public List<MatchingCriteria> getMatchingCriteria() {
        return matchingCriteria;
    }

    public void setMatchingCriteria(List<MatchingCriteria> matchingCriteria) {
        this.matchingCriteria = matchingCriteria;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PassConfiguration)) return false;
        PassConfiguration that = (PassConfiguration) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
