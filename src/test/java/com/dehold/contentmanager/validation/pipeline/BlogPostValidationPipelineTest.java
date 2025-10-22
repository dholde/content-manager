package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.step.LengthValidator;
import com.dehold.contentmanager.validation.step.ValidationStep;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlogPostValidationPipelineTest {

    @Test
    void runPipeline_allStepsPass_returnsTrue() {
        ValidationPipeline pipeline = new BlogPostValidationPipeline();
        List<ValidationStep> steps = List.of(new LengthValidator(1,250));
        assertTrue(pipeline.runPipeline("hello", steps));
    }

    @Test
    void runPipeline_stepFails_returnsFalse() {
        ValidationPipeline pipeline = new BlogPostValidationPipeline();
        ValidationStep failing = value -> false;
        List<ValidationStep> steps = List.of(new LengthValidator(1,250), failing);
        assertFalse(pipeline.runPipeline("hello", steps));
    }

    @Test
    void runPipeline_emptySteps_returnsTrue() {
        ValidationPipeline pipeline = new BlogPostValidationPipeline();
        assertTrue(pipeline.runPipeline("any", List.of()));
    }

    @Test
    void runPipeline_nullStepsHandled() {
        ValidationPipeline pipeline = new BlogPostValidationPipeline();
        assertTrue(pipeline.runPipeline("any", null));
    }

}
