package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.validation.result.ValidationError;
import com.dehold.contentmanager.validation.result.ValidationResult;

import java.util.List;
import java.util.Objects;

public class ValidationResultDto {
    private boolean valid;
    private List<ValidationError> errors;

    public ValidationResultDto() {
    }

    public ValidationResultDto(boolean valid, List<ValidationError> errors) {
        this.valid = valid;
        this.errors = errors;
    }

    public static ValidationResultDto from(ValidationResult validationResult) {
        return new ValidationResultDto(validationResult.isValid(), validationResult.getErrors());
    }

    public ValidationResult toValidationResult() {
        return valid ? ValidationResult.valid() : ValidationResult.invalid(errors);
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
