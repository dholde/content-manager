package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.step.ValidationStep;

import java.util.List;

public class BlockPostValidationPipeline implements ValidationPipeline {

    private final List<ValidationStep> steps;

    public BlockPostValidationPipeline(List<ValidationStep> steps) {
        this.steps = steps;
    }

    @Override
    public boolean runPipeline(String value, int minLength, int maxLength) {
        if (steps == null || steps.isEmpty()) {
            return true;
        }
        for (ValidationStep step : steps) {
            if (!step.validate(value, minLength, maxLength)) {
                return false;
            }
        }
        return true;
    }
}

