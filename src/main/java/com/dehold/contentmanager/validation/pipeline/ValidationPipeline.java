package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.result.ValidationResult;
import com.dehold.contentmanager.validation.step.ValidationStep;

import java.util.List;

public interface ValidationPipeline<T> {
    ValidationResult run(T content);
}
