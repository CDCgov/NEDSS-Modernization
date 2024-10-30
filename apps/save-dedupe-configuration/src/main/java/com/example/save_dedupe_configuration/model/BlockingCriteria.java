package com.example.save_dedupe_configuration.model;

import com.example.save_dedupe_configuration.model.BlockingCriteria;
import com.example.save_dedupe_configuration.model.MatchingCriteria;
import com.example.save_dedupe_configuration.model.PassConfiguration;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "blocking_criteria")
public class BlockingCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pass_configuration_id", nullable = false)
    private PassConfiguration passConfiguration;

    @ManyToOne
    @JoinColumn(name = "data_element_id", nullable = false)
    private DataElement dataElement;

    @ManyToOne
    @JoinColumn(name = "method_id", nullable = false)
    private Method method;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public BlockingCriteria() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PassConfiguration getPassConfiguration() {
        return passConfiguration;
    }

    public void setPassConfiguration(PassConfiguration passConfiguration) {
        this.passConfiguration = passConfiguration;
    }

    public DataElement getDataElement() {
        return dataElement;
    }

    public void setDataElement(DataElement dataElement) {
        this.dataElement = dataElement;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockingCriteria)) return false;
        BlockingCriteria that = (BlockingCriteria) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
