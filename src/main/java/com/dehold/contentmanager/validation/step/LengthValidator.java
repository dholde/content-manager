package com.dehold.contentmanager.validation.step;

import com.dehold.contentmanager.validation.result.ValidationError;
import com.dehold.contentmanager.validation.result.ValidationResult;

import java.util.List;

public class LengthValidator implements ValidationStep {

    private int minLength;
    private int maxLength;
    public static final String ERROR_CODE = "LENGTH_VALIDATION_FAILED";
    public static final String ERROR_MESSAGE_TOO_SHORT = "The Text is too short.";
    public static final String ERROR_MESSAGE_TOO_LONG = "The Text is too long.";
    public static final String ERROR_MESSAGE_NULL = "No text provided (null).";

    public LengthValidator(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public ValidationResult validate(String content) {
        return this.validate(content, minLength, maxLength);
    }

    private ValidationResult validate(String value, int minLength, int maxLength) {
        if (minLength < 0 || maxLength < 0 || minLength > maxLength) {
            throw new IllegalArgumentException("Invalid min/max lengths");
        }
        if (value == null) {
            return ValidationResult.invalid(List.of());
        }
        if(value.length() < minLength) return ValidationResult.invalid(List.of(new ValidationError(ERROR_CODE, ERROR_MESSAGE_TOO_SHORT)));
        if(value.length() > maxLength) return ValidationResult.invalid(List.of(new ValidationError(ERROR_CODE,
                ERROR_MESSAGE_TOO_LONG)));
        int len = value.length();
        return len >= minLength && len <= maxLength ? ValidationResult.valid() : ValidationResult.invalid(List.of());
    }
}
