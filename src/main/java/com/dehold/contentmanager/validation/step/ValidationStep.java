package com.dehold.contentmanager.validation.step;

import com.dehold.contentmanager.validation.result.ValidationResult;

public interface ValidationStep {
    ValidationResult validate(String content);
}
