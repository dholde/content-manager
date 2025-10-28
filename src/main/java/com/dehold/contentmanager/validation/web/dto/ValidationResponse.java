package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.validation.result.ValidationResult;
import java.util.Objects;

public class ValidationResponse {
    private final String contentType;
    private final ValidationResult validationResult;

    public ValidationResponse(String contentType, ValidationResult validationResult) {
        this.contentType = contentType;
        this.validationResult = validationResult;
    }

    public String getContentType() {
        return contentType;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationResponse that = (ValidationResponse) o;
        return Objects.equals(contentType, that.contentType) &&
               Objects.equals(validationResult, that.validationResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentType, validationResult);
    }
}
