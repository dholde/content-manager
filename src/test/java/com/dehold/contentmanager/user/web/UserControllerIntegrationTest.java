package com.dehold.contentmanager.user.web;

import com.dehold.contentmanager.user.model.User;
import com.dehold.contentmanager.user.repository.UserRepository;
import com.dehold.contentmanager.user.web.dto.CreateUserRequest;
import com.dehold.contentmanager.user.web.dto.UpdateUserRequest;
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

        ResponseEntity<String> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/users/" + nonExistentId,
            String.class
        );

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("The entity User with id " + nonExistentId + " does not exist"));
    }
}
