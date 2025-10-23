package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.step.ValidationStep;

import java.util.List;

public class BlogPostValidationPipeline implements ValidationPipeline {

    @Override
    public boolean runPipeline(String value, List<ValidationStep> steps) {
        if (steps == null || steps.isEmpty()) {
            return true;
        }
        for (ValidationStep step : steps) {
            if (!step.validate(value).isValid()) {
                return false;
            }
        }
        return true;
    }
}
