package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.step.LengthValidator;
import com.dehold.contentmanager.validation.step.ValidationStep;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockPostValidationPipelineTest {

    @Test
    void runPipeline_allStepsPass_returnsTrue() {
        ValidationPipeline pipeline = new BlockPostValidationPipeline();
        List<ValidationStep> steps = List.of(new LengthValidator(1,250));
        assertTrue(pipeline.runPipeline("hello", steps));
    }

    @Test
    void runPipeline_stepFails_returnsFalse() {
        ValidationPipeline pipeline = new BlockPostValidationPipeline();
        ValidationStep failing = value -> false;
        List<ValidationStep> steps = List.of(new LengthValidator(1,250), failing);
        assertFalse(pipeline.runPipeline("hello", steps));
    }

    @Test
    void runPipeline_emptySteps_returnsTrue() {
        ValidationPipeline pipeline = new BlockPostValidationPipeline();
        assertTrue(pipeline.runPipeline("any", List.of()));
    }

    @Test
    void runPipeline_nullStepsHandled() {
        ValidationPipeline pipeline = new BlockPostValidationPipeline();
        assertTrue(pipeline.runPipeline("any", null));
    }

}
