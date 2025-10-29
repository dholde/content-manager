package com.dehold.contentmanager.validation.model;

import java.util.List;
import java.util.UUID;

public class ValidationResult {
    private final String contentType;
    private final UUID contentId;
    private final boolean isValid;
    private final List<ValidationError> errors;

    private ValidationResult(String contentType, UUID contentId, boolean isValid, List<ValidationError> errors) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.isValid = isValid;
        this.errors = errors;
    }

    public static ValidationResult valid(String contentType, UUID contentId) {
        return new ValidationResult(contentType, contentId, true, List.of());
    }

    public static ValidationResult invalid(String contentType, UUID contentId, List<ValidationError> errors) {
        return new ValidationResult(contentType, contentId, false, errors);
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
}
