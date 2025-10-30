package com.dehold.contentmanager.user.web;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.content.blogpost.repository.BlogPostRepository;
import com.dehold.contentmanager.exception.CustomErrorResponse;
import com.dehold.contentmanager.user.model.User;
import com.dehold.contentmanager.user.repository.UserRepository;
import com.dehold.contentmanager.user.web.dto.CreateUserRequest;
import com.dehold.contentmanager.user.web.dto.UpdateUserRequest;
import com.dehold.contentmanager.validation.model.ValidationResult;
import com.dehold.contentmanager.validation.web.dto.BlogPostValidationRequest;
import com.dehold.contentmanager.validation.web.dto.ValidationResponse;
import com.dehold.contentmanager.validation.web.dto.ValidationResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Test
    void createUser_shouldReturnCreatedUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setAlias("Integration Test User");
        request.setEmail("integration-" + UUID.randomUUID() + "@example.com");
        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + port + "/api/users", request, User.class);
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(request.getAlias(), response.getBody().getAlias());
        assertEquals(request.getEmail(), response.getBody().getEmail());
    }

    @Test
    void getUser_shouldReturnUser() {
        String uniqueEmail = "integration-" + UUID.randomUUID() + "@example.com";
        User user = new User(UUID.randomUUID(), "Integration Test User", uniqueEmail, Instant.now(), Instant.now());
        userRepository.createUser(user);
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/api/users/" + user.getId(), User.class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(user.getAlias(), response.getBody().getAlias());
        assertEquals(user.getEmail(), response.getBody().getEmail());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        String oldEmail = "old-" + UUID.randomUUID() + "@example.com";
        String newEmail = "new-" + UUID.randomUUID() + "@example.com";
        User user = new User(UUID.randomUUID(), "Old Name", oldEmail, Instant.now(), Instant.now());
        userRepository.createUser(user);
        UpdateUserRequest request = new UpdateUserRequest();
        request.setAlias("New Name");
        request.setEmail(newEmail);
        HttpEntity<UpdateUserRequest> entity = new HttpEntity<>(request);
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:" + port + "/api/users/" + user.getId(), HttpMethod.PUT, entity, User.class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(request.getAlias(), response.getBody().getAlias());
        assertEquals(request.getEmail(), response.getBody().getEmail());
    }

    @Test
    void getUser_shouldReturnNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        ResponseEntity<CustomErrorResponse> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/users/" + nonExistentId,
                CustomErrorResponse.class
        );
        assertNotNull(response.getBody());
        CustomErrorResponse errorResponse = response.getBody();
        assertEquals(404, errorResponse.getHttpStatusCode());
        assertNotNull(response.getBody());
        assertEquals("The entity User with id " + nonExistentId + " does not exist", errorResponse.getError());
    }

    @Test
    void getBlogpostsByUserId_shouldReturnBlogposts() {
        User user = new User(UUID.randomUUID(), "Blogpost User", "blogpostuser-" + UUID.randomUUID() + "@example.com", Instant.now(), Instant.now());
        userRepository.createUser(user);
        BlogPost blogPost = new BlogPost(UUID.randomUUID(), "Test Blogpost", "This is a test blogpost.", Instant.now(), Instant.now(), user.getId());
        blogPostRepository.createBlogPost(blogPost);

        ResponseEntity<BlogPost[]> response = restTemplate.getForEntity("http://localhost:" + port + "/api/users" +
                "/" + user.getId() + "/blogposts", BlogPost[].class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals(blogPost.getId(), response.getBody()[0].getId());
    }

    @Test
    void getBlogPostsForNonExistentUser_shouldReturnNotFoundResponseCode() {
        UUID nonExistentUserId = UUID.randomUUID();

        ResponseEntity<CustomErrorResponse> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/users/" + nonExistentUserId + "/blogposts",
                CustomErrorResponse.class
        );

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        CustomErrorResponse errorResponse = response.getBody();
        assertEquals(404, errorResponse.getHttpStatusCode());
    }

    @Test
    void getBlogPostsForNonExistentUser_shouldReturnNotFoundErrorMessage() {
        UUID nonExistentUserId = UUID.randomUUID();

        ResponseEntity<CustomErrorResponse> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/users/" + nonExistentUserId + "/blogposts",
                CustomErrorResponse.class
        );

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        CustomErrorResponse errorResponse = response.getBody();
        assertEquals("The entity User with id " + nonExistentUserId + " does not exist", errorResponse.getError());
    }

    @Test
    void getBlogPostsForNonExistentUser_shouldReturnNotFoundErrorPath() {
        UUID nonExistentUserId = UUID.randomUUID();

        ResponseEntity<CustomErrorResponse> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/users/" + nonExistentUserId + "/blogposts",
                CustomErrorResponse.class
        );

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        CustomErrorResponse errorResponse = response.getBody();
        assertTrue(errorResponse.getPath().contains("/api/users/" + nonExistentUserId + "/blogposts"));
    }

    @Test
    void getBlogPostsForExistentUserThatHasNoPosts_shouldReturnEmptyList() {
        User user = new User(UUID.randomUUID(), "Blogpost User", "blogpostuser-" + UUID.randomUUID() + "@example.com", Instant.now(), Instant.now());
        userRepository.createUser(user);

        ResponseEntity<BlogPost[]> response = restTemplate.getForEntity("http://localhost:" + port + "/api/users" +
                "/" + user.getId() + "/blogposts", BlogPost[].class);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);
    }

    @Test
    void getValidationResultsByUser_shouldReturnValidationResults() {
        User user = new User(UUID.randomUUID(), "Validation User", "validationuser-" + UUID.randomUUID() + "@example.com", Instant.now(), Instant.now());
        userRepository.createUser(user);

        BlogPost blogPost = new BlogPost(UUID.randomUUID(), "Validation Blogpost", "This is a validation blogpost.", Instant.now(), Instant.now(), user.getId());
        blogPostRepository.createBlogPost(blogPost);

        BlogPostValidationRequest request = new BlogPostValidationRequest(3, 100, 10, 1000, blogPost);
        ValidationResponse validationResponse = restTemplate.postForEntity("http://localhost:" + port + "/api" +
                "/validate/blogpost", request, ValidationResponse.class).getBody();
        assertNotNull(validationResponse);
        ValidationResult validationResult = validationResponse.getValidationResult().toValidationResult();

        ValidationResultDto[] validationResults = restTemplate.getForEntity("http://localhost:" + port + "/api/users/" +
                user.getId() + "/validation-results", ValidationResultDto[].class).getBody();
        assertNotNull(validationResults);
        assertEquals(1, validationResults.length);

    }
}
