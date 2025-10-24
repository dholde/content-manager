package com.dehold.contentmanager.validation.step;

import com.dehold.contentmanager.validation.result.ValidationError;
import com.dehold.contentmanager.validation.result.ValidationResult;

import java.util.List;
import java.util.function.Function;

public class LengthValidator<T> implements ValidationStep<T> {

    private final Function<T, String> getter;
    private final String fieldName;
    private final int minLength;
    private final int maxLength;
    public static final String ERROR_CODE = "LENGTH_VALIDATION_FAILED";
    public static final String ERROR_MESSAGE_TOO_SHORT = "The Text is too short.";
    public static final String ERROR_MESSAGE_TOO_LONG = "The Text is too long.";

    public LengthValidator(Function<T, String> getter, String fieldName, int minLength, int maxLength) {
        this.getter = getter;
        this.fieldName = fieldName;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public ValidationResult validate(T content) {
        String value = getter.apply(content);
        if (minLength < 0 || maxLength < 0 || minLength > maxLength) {
            throw new IllegalArgumentException("Invalid min/max lengths");
        }
        if(value.length() < minLength) return ValidationResult.invalid(List.of(new ValidationError(ERROR_CODE, ERROR_MESSAGE_TOO_SHORT)));
        if(value.length() > maxLength) return ValidationResult.invalid(List.of(new ValidationError(ERROR_CODE,
                ERROR_MESSAGE_TOO_LONG)));
        return ValidationResult.valid();
    }
}
