package com.dehold.contentmanager.tenant.web.dto;

import java.time.Instant;
import java.util.UUID;

public class TenantResponse {

    private UUID id;
    private String name;
    private String identifier;
    private Instant createdAt;
    private Instant updatedAt;

    public TenantResponse(UUID id, String name, String identifier, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.identifier = identifier;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
