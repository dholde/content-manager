package com.dehold.contentmanager.validation.web;


import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.validation.result.ValidationError;
import com.dehold.contentmanager.validation.result.ValidationResult;
import com.dehold.contentmanager.validation.step.LengthValidator;
import com.dehold.contentmanager.validation.web.dto.BlogPostValidationRequest;
import com.dehold.contentmanager.validation.web.dto.ValidationResponse;
import com.dehold.contentmanager.validation.web.dto.ValidationResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidationControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenValidBlogPost_whenValidate_thenReturnsSuccess() {
        BlogPost blogPost = new BlogPost(UUID.randomUUID(), "Valid Title", "This is valid content for the blog post."
                , Instant.now(), Instant.now(), UUID.randomUUID());
        BlogPostValidationRequest request = new BlogPostValidationRequest(3, 100, 10, 1000, blogPost);

        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/validate/blogpost", request,
                ValidationResponse.class);

        assertEquals(200, response.getStatusCode().value());
        ValidationResponse expected = new ValidationResponse(BlogPost.class.getSimpleName(),
                ValidationResultDto.from(ValidationResult.valid()));
        ValidationResponse actual = response.getBody();
        assertNotNull(actual);
        assertEquals(expected.getContentType(), actual.getContentType());
        assertEquals(expected.getValidationResult().isValid(), actual.getValidationResult().isValid());
    }

    @Test
    void givenBlogPostWithInvalidTitle_whenValidate_thenReturnsValidationError() {
        BlogPost blogPost = new BlogPost(UUID.randomUUID(), "Not a Valid Title: Is too short", "This is valid content" +
                " for the blog " +
                "post."
                , Instant.now(), Instant.now(), UUID.randomUUID());
        BlogPostValidationRequest request = new BlogPostValidationRequest(50, 100, 10, 1000, blogPost);

        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/validate/blogpost", request,
                ValidationResponse.class);

        assertEquals(200, response.getStatusCode().value());
        var validationErrors = List.of(
                new ValidationError(LengthValidator.ERROR_CODE,
                        LengthValidator.errorMessageTooShort("title")));
        ValidationResponse expected = new ValidationResponse(BlogPost.class.getSimpleName(),
                ValidationResultDto.from(ValidationResult.invalid(validationErrors)));
        ValidationResponse actual = response.getBody();
        assertNotNull(actual);
        assertEquals(expected.getContentType(), actual.getContentType());
        assertEquals(1, actual.getValidationResult().getErrors().size());
        ValidationError actualError = actual.getValidationResult().getErrors().get(0);
        assertEquals(actualError.code(), validationErrors.getFirst().code());
        assertEquals(actualError.message(), validationErrors.getFirst().message());
    }

    @Test
    void givenBlogPostWithInvalidContent_whenValidate_thenReturnsValidationError() {
        BlogPost blogPost = new BlogPost(UUID.randomUUID(), "Valid Title", "This content is not valid: Too short", Instant.now(), Instant.now(), UUID.randomUUID());
        BlogPostValidationRequest request = new BlogPostValidationRequest(2, 100, 300, 1000, blogPost);

        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/validate/blogpost", request,
                ValidationResponse.class);

        assertEquals(200, response.getStatusCode().value());
        var validationErrors = List.of(
                new ValidationError(LengthValidator.ERROR_CODE,
                        LengthValidator.errorMessageTooShort("content")));
        ValidationResponse expected = new ValidationResponse(BlogPost.class.getSimpleName(),
                ValidationResultDto.from(ValidationResult.invalid(validationErrors)));
        ValidationResponse actual = response.getBody();
        assertNotNull(actual);
        assertEquals(expected.getContentType(), actual.getContentType());
        assertEquals(1, actual.getValidationResult().getErrors().size());
        ValidationError actualError = actual.getValidationResult().getErrors().get(0);
        assertEquals(actualError.code(), validationErrors.getFirst().code());
        assertEquals(actualError.message(), validationErrors.getFirst().message());
    }

    @Test
    void givenBlogPostWithInvalidField_whenValidate_thenReturnsValidationError() {
        BlogPost blogPost = new BlogPost(UUID.randomUUID(), "Valid Title", "This content is not valid: Too short", Instant.now(), Instant.now(), UUID.randomUUID());
        BlogPostValidationRequest request = new BlogPostValidationRequest(2, 100, 300, 1000, blogPost);

        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/validate/blogpost", request,
                ValidationResponse.class);

        assertEquals(200, response.getStatusCode().value());
        var validationErrors = List.of(
                new ValidationError(LengthValidator.ERROR_CODE,
                        LengthValidator.errorMessageTooShort("content")));
        ValidationResponse expected = new ValidationResponse(BlogPost.class.getSimpleName(),
                ValidationResultDto.from(ValidationResult.invalid(validationErrors)));
        ValidationResponse actual = response.getBody();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}