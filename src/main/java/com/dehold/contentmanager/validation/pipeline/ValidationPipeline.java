package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.model.ValidationResult;

public interface ValidationPipeline<T> {
    ValidationResult run(T content);
}
