package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.validation.model.ValidationError;
import com.dehold.contentmanager.validation.model.ValidationResult;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ValidationResultDto {
    private String contentType;
    private UUID contentId;
    private boolean valid;
    private List<ValidationError> errors;

    public ValidationResultDto() {
    }

    public ValidationResultDto(String contentType, UUID contentId, boolean valid, List<ValidationError> errors) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.valid = valid;
        this.errors = errors;
    }

    public static ValidationResultDto from(ValidationResult validationResult) {
        return new ValidationResultDto(validationResult.getContentType(),
                validationResult.getContentId(), validationResult.isValid(),
                validationResult.getErrors());
    }

    public ValidationResult toValidationResult() {
        return valid ? ValidationResult.valid(this.contentType, this.contentId) :
                ValidationResult.invalid(this.contentType, this.contentId, errors);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    public String getContentType() {
        return contentType;
    }

    public UUID getContentId() {
        return contentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationResultDto that = (ValidationResultDto) o;
        return valid == that.valid && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valid, errors);
    }
}
