package com.dehold.contentmanager.validation.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ValidationResult {
    private final UUID id;
    private final String contentType;
    private final UUID contentId;
    private final boolean isValid;
    private final List<ValidationError> errors;
    private final Instant createdAt;

    private ValidationResult(String contentType, UUID contentId, boolean isValid, List<ValidationError> errors) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.isValid = isValid;
        this.errors = errors;
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
    }

    private ValidationResult(UUID id, String contentType, UUID contentId, boolean isValid, List<ValidationError> errors, Instant createdAt) {
        this.id = id;
        this.contentType = contentType;
        this.contentId = contentId;
        this.isValid = isValid;
        this.errors = errors;
        this.createdAt = createdAt;
    }

    public static ValidationResult valid(String contentType, UUID contentId) {
        return new ValidationResult(contentType, contentId, true, List.of());
    }

    public static ValidationResult invalid(String contentType, UUID contentId, List<ValidationError> errors) {
        return new ValidationResult(contentType, contentId, false, errors);
    }

    public static ValidationResult fromPersistence(UUID id, String contentType, UUID contentId, boolean isValid, List<ValidationError> errors, Instant createdAt) {
        return new ValidationResult(id, contentType, contentId, isValid, errors, createdAt);
    }

    public boolean isValid() {
        return isValid;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public String getContentType() {
        return contentType;
    }

    public UUID getContentId() {
        return contentId;
    }

    public UUID getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
