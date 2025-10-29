package com.dehold.contentmanager.validation.model;

import java.util.List;

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

}
