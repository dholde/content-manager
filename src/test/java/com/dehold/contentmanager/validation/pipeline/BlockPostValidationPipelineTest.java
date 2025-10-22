package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.step.LengthValidator;
import com.dehold.contentmanager.validation.step.ValidationStep;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockPostValidationPipelineTest {

    @Test
    void runPipeline_allStepsPass_returnsTrue() {
        ValidationPipeline pipeline = new BlockPostValidationPipeline(List.of(new LengthValidator()));
        assertTrue(pipeline.runPipeline("hello", 1, 10));
    }

    @Test
    void runPipeline_stepFails_returnsFalse() {
        ValidationStep failing = (value, min, max) -> false;
        ValidationPipeline pipeline = new BlockPostValidationPipeline(List.of(new LengthValidator(), failing));
        assertFalse(pipeline.runPipeline("hello", 1, 10));
    }

    @Test
    void runPipeline_emptySteps_returnsTrue() {
        ValidationPipeline pipeline = new BlockPostValidationPipeline(List.of());
        assertTrue(pipeline.runPipeline("any", 0, 5));
    }

    @Test
    void runPipeline_nullStepsHandled() {
        ValidationPipeline pipeline = new BlockPostValidationPipeline(null);
        assertTrue(pipeline.runPipeline("any", 0, 5));
    }

}

