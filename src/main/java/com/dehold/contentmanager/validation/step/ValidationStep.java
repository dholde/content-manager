package com.dehold.contentmanager.validation.step;

import com.dehold.contentmanager.validation.model.ValidationResult;

public interface ValidationStep<T> {
    ValidationResult validate(T content);

    String getFieldName();
}
