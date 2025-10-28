package com.dehold.contentmanager.validation.result;

import java.util.List;
import java.util.Objects;

public class ValidationResult {
    private final boolean isValid;
    private final List<ValidationError> errors;

    private ValidationResult(boolean isValid, List<ValidationError> errors) {
        this.isValid = isValid;
        this.errors = errors;
    }

    public static ValidationResult valid() {
        return new ValidationResult(true, List.of());
    }

    public static ValidationResult invalid(List<ValidationError> errors) {
        return new ValidationResult(false, errors);
    }

    public boolean isValid() {
        return isValid;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationResult that = (ValidationResult) o;
        return isValid == that.isValid && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isValid, errors);
    }
}
