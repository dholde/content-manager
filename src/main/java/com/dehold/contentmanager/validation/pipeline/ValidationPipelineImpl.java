package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.result.ValidationError;
import com.dehold.contentmanager.validation.result.ValidationResult;
import com.dehold.contentmanager.validation.step.ValidationStep;

import java.util.ArrayList;
import java.util.List;

final class ValidationPipelineImpl<T> implements ValidationPipeline<T> {
    private final List<ValidationStep<T>> validationSteps;

    public ValidationPipelineImpl(List<ValidationStep<T>> validationSteps) {
        this.validationSteps = validationSteps;
    }

    @Override
    public ValidationResult run(T content) {
        List<ValidationError> validationErrors = new ArrayList<>();
        for(ValidationStep<T> step : validationSteps) {
            var result = step.validate(content);
            if(!result.isValid()) {
                validationErrors.addAll(result.getErrors());
            }
        }
        return validationErrors.isEmpty() ? ValidationResult.valid() : ValidationResult.invalid(validationErrors);
    }
}
