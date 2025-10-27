package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.validation.result.ValidationResult;
import com.dehold.contentmanager.validation.step.LengthValidator;
import com.dehold.contentmanager.validation.step.PhoneNumberForbiddenValidator;
import com.dehold.contentmanager.validation.step.ValidationStep;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidationPipelineBuilderTest {

    @Test
    void givenValidContent_whenRunPipelineWithOneValidationStep_thenShouldReturnValid() {
        BlogPost post = new BlogPost(null, "Title", "Valid Content", null, null, null);
        ValidationStep<BlogPost> lengthValidator = new LengthValidator<>(BlogPost::getContent, "content", 5, 20);

        ValidationPipeline<BlogPost> pipeline = new ValidationPipelineBuilder<BlogPost>()
                .addStep(lengthValidator)
                .build();

        ValidationResult result = pipeline.run(post);
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void givenInvalidContent_whenRunPipelineWithOneValidationStep_thenShouldReturnError() {
        BlogPost post = new BlogPost(null, "Title", "Short", null, null, null);
        ValidationStep<BlogPost> lengthValidator = new LengthValidator<>(BlogPost::getContent, "content", 10, 20);

        ValidationPipeline<BlogPost> pipeline = new ValidationPipelineBuilder<BlogPost>()
                .addStep(lengthValidator)
                .build();

        ValidationResult result = pipeline.run(post);
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("LENGTH_VALIDATION_FAILED", result.getErrors().getFirst().code());
        assertEquals("The field 'content' is too short.", result.getErrors().getFirst().message());
    }

    @Test
    void givenValidContent_whenRunPipelineWithTwoValidationSteps_thenShouldReturnValid() {
        BlogPost post = new BlogPost(null, "Title", "Valid Content", null, null, null);

        ValidationStep<BlogPost> lengthValidator = new LengthValidator<>(BlogPost::getContent, "content", 5, 20);
        ValidationStep<BlogPost> phoneNumberForbiddenValidator = new PhoneNumberForbiddenValidator<>(BlogPost::getContent, "content");

        ValidationPipeline<BlogPost> pipeline = new ValidationPipelineBuilder<BlogPost>()
                .addStep(lengthValidator)
                .addStep(phoneNumberForbiddenValidator)
                .build();

        ValidationResult result = pipeline.run(post);
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }
}