package com.dehold.contentmanager.content.customersupport.model;

import com.dehold.contentmanager.content.Content;

import java.time.Instant;
import java.util.UUID;

public class SupportResponse implements Content {
    private UUID id;
    private String text;
    private UUID supportRequest;
    private Instant createdAt;
    private Instant updatedAt;

    public SupportResponse() {}

    public SupportResponse(UUID id, String text, UUID supportRequest, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.text = text;
        this.supportRequest = supportRequest;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getSupportRequest() {
        return supportRequest;
    }

    public void setSupportRequest(UUID supportRequest) {
        this.supportRequest = supportRequest;
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

