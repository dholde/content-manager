package com.dehold.contentmanager.validation.web;


import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.validation.result.ValidationError;
import com.dehold.contentmanager.validation.result.ValidationResult;
import com.dehold.contentmanager.validation.step.LengthValidator;
import com.dehold.contentmanager.validation.web.dto.BlogPostValidationRequest;
import com.dehold.contentmanager.validation.web.dto.ValidationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/validate/blog-post", request,
                ValidationResponse.class);

        assertEquals(200, response.getStatusCode().value());
        ValidationResponse expected = new ValidationResponse("BlogPOSSSST",
                ValidationResult.valid());
        assertEquals(expected, response.getBody());
    }
/*
    @Test
    void givenInvalidBlogPost_whenValidate_thenReturnsValidationError() {
        BlogPost blogPost = new BlogPost(UUID.randomUUID(), "Not a Valid Title: Is too short", "This is valid content" +
                " for the blog " +
                "post."
                , Instant.now(), Instant.now(), UUID.randomUUID());
        BlogPostValidationRequest request = new BlogPostValidationRequest(50, 100, 10, 1000, blogPost);

        var response = restTemplate.postForEntity("http://localhost:" + port + "/api/validate/blog-post", request,
                ValidationResponse.class);

        assertEquals(200, response.getStatusCode().value());
        var validationErrors = List.of(
                new ValidationError(LengthValidator.ERROR_CODE,
                        LengthValidator.errorMessageTooShort("title")));
        ValidationResponse expected = new ValidationResponse(BlogPost.class.getSimpleName(),
                ValidationResult.invalid(validationErrors));
        assertEquals(expected, response.getBody());
    }*/

}