package com.dehold.contentmanager.content.blogpost.web;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.content.blogpost.repository.BlogPostRepository;
import com.dehold.contentmanager.content.blogpost.web.dto.CreateBlogPostRequest;
import com.dehold.contentmanager.content.blogpost.web.dto.UpdateBlogPostRequest;
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
class BlogPostControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Test
    void createBlogPost_shouldReturnCreatedBlogPost() {
        CreateBlogPostRequest request = new CreateBlogPostRequest();
        request.setTitle("Integration Test Blog Post");
        request.setContent("This is a test blog post for integration testing.");

        ResponseEntity<BlogPost> response = restTemplate.postForEntity("http://localhost:" + port + "/api/blogposts", request, BlogPost.class);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(request.getTitle(), response.getBody().getTitle());
        assertEquals(request.getContent(), response.getBody().getContent());
    }

    @Test
    void getBlogPost_shouldReturnBlogPost() {
        BlogPost blogPost = new BlogPost(UUID.randomUUID(), "Integration Test Blog Post", "This is a test blog post for integration testing.", Instant.now(), Instant.now());
        blogPostRepository.createBlogPost(blogPost);

        ResponseEntity<BlogPost> response = restTemplate.getForEntity("http://localhost:" + port + "/api/blogposts/" + blogPost.getId(), BlogPost.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(blogPost.getId(), response.getBody().getId());
    }

    @Test
    void updateBlogPost_shouldReturnUpdatedBlogPost() {
        BlogPost blogPost = new BlogPost(UUID.randomUUID(), "Old Title", "Old Content", Instant.now(), Instant.now());
        blogPostRepository.createBlogPost(blogPost);

        UpdateBlogPostRequest request = new UpdateBlogPostRequest();
        request.setTitle("Updated Title");
        request.setContent("Updated Content");

        HttpEntity<UpdateBlogPostRequest> entity = new HttpEntity<>(request);
        ResponseEntity<BlogPost> response = restTemplate.exchange("http://localhost:" + port + "/api/blogposts/" + blogPost.getId(), HttpMethod.PUT, entity, BlogPost.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(request.getTitle(), response.getBody().getTitle());
        assertEquals(request.getContent(), response.getBody().getContent());
    }

    @Test
    void deleteBlogPost_shouldDeleteBlogPost() {
        CreateBlogPostRequest createRequest = new CreateBlogPostRequest();
        createRequest.setTitle("Integration Test Blog Post");
        createRequest.setContent("This is a test blog post for integration testing.");

        ResponseEntity<BlogPost> createResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/blogposts", createRequest, BlogPost.class);
        assertEquals(201, createResponse.getStatusCode().value());
        assertNotNull(createResponse.getBody());

        UUID blogPostId = createResponse.getBody().getId();

        restTemplate.delete("http://localhost:" + port + "/api/blogposts/" + blogPostId);

        ResponseEntity<BlogPost> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/api/blogposts/" + blogPostId, BlogPost.class);
        assertEquals(404, getResponse.getStatusCode().value()); // Now correctly returns 404 Not Found
    }

    @Test
    void getBlogPost_shouldReturnNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        ResponseEntity<Void> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/blogposts/" + nonExistentId,
            Void.class
        );

        assertEquals(404, response.getStatusCode().value());
    }
}
