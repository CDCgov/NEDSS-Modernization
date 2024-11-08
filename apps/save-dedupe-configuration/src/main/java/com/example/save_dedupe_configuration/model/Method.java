package com.example.save_dedupe_configuration.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import com.example.save_dedupe_configuration.deserializer.MethodDeserializer;

@Entity
@Table(name = "methods")
@JsonDeserialize(using = MethodDeserializer.class)
public class Method {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_type", nullable = false)
    private MethodType methodType;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor
    public Method() {}

    @JsonCreator
    public Method(
            @JsonProperty("methodType") MethodType methodType,
            @JsonProperty("name") String name,
            @JsonProperty("value") String value) {
        this.methodType = methodType;
        this.name = name;
        this.value = value;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public MethodType getMethodType() { return methodType; }
    public void setMethodType(MethodType methodType) { this.methodType = methodType; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    // Lifecycle methods
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
        if (!(o instanceof Method)) return false;
        Method method = (Method) o;
        return Objects.equals(id, method.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
