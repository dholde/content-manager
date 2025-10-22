package com.dehold.contentmanager.tenant.web.dto;

import java.time.Instant;
import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String alias;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;

    public UserResponse(UUID id, String alias, String email, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.alias = alias;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
