package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.step.ValidationStep;

import java.util.List;

final class ValidationPipelineImpl<T> implements ValidationPipeline<T> {
    private final List<ValidationStep<T>> validationSteps;

    public ValidationPipelineImpl(List<ValidationStep<T>> validationSteps) {
        this.validationSteps = validationSteps;
    }

    @Override
    public boolean run(T content) {
        return false;
    }
}
