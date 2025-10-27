package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.step.ValidationStep;

import java.util.ArrayList;
import java.util.List;

public final class ValidationPipelineBuilder<T> {
    List<ValidationStep<T>> steps = new ArrayList<>();
    public ValidationPipelineBuilder<T> addStep(ValidationStep<T> step) {
        steps.add(step);
        return this;
    }

    public ValidationPipeline<T> build() {
        return new ValidationPipelineImpl<>(steps);
    }

}
