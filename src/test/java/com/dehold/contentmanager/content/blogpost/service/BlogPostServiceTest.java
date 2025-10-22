package com.dehold.contentmanager.content.blogpost.service;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.content.blogpost.repository.BlogPostRepository;
import com.dehold.contentmanager.content.blogpost.service.BlogPostService;
import com.dehold.contentmanager.content.blogpost.web.dto.CreateBlogPostRequest;
import com.dehold.contentmanager.content.blogpost.web.dto.UpdateBlogPostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlogPostServiceTest {

    @Mock
    private BlogPostRepository blogPostRepository;

    @InjectMocks
    private BlogPostService blogPostService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBlogPost_shouldCreateAndReturnBlogPost() {
        CreateBlogPostRequest request = new CreateBlogPostRequest();
        request.setTitle("Test Blog Post");
        request.setContent("This is a test blog post.");
        UUID userId = UUID.randomUUID();
        request.setUserId(userId);

        BlogPost blogPost = new BlogPost(UUID.randomUUID(), "Test Blog Post", "This is a test blog post.",
                Instant.now(), Instant.now(), UUID.randomUUID());

        doNothing().when(blogPostRepository).createBlogPost(any(BlogPost.class));

        BlogPost createdBlogPost = blogPostService.createBlogPost(request.getTitle(), request.getContent(),
                request.getUserId());

        assertNotNull(createdBlogPost);
        assertEquals(request.getTitle(), createdBlogPost.getTitle());
        assertEquals(request.getContent(), createdBlogPost.getContent());
        verify(blogPostRepository, times(1)).createBlogPost(any(BlogPost.class));
    }

    @Test
    void getBlogPost_shouldReturnBlogPostIfExists() {
        UUID blogPostId = UUID.randomUUID();
        BlogPost blogPost = new BlogPost(blogPostId, "Test Blog Post", "This is a test blog post.", Instant.now(),
                Instant.now(), UUID.randomUUID());

        when(blogPostRepository.getBlogPost(blogPostId)).thenReturn(Optional.of(blogPost));

        BlogPost foundBlogPost = blogPostService.getBlogPost(blogPostId);

        assertNotNull(foundBlogPost);
        assertEquals(blogPostId, foundBlogPost.getId());
        verify(blogPostRepository, times(1)).getBlogPost(blogPostId);
    }

    @Test
    void updateBlogPost_shouldUpdateAndReturnUpdatedBlogPost() {
        UUID blogPostId = UUID.randomUUID();
        BlogPost existingBlogPost = new BlogPost(blogPostId, "Old Title", "Old Content", Instant.now(), Instant.now()
                , UUID.randomUUID());
        UpdateBlogPostRequest request = new UpdateBlogPostRequest();
        request.setTitle("New Title");
        request.setContent("New Content");

        when(blogPostRepository.getBlogPost(blogPostId)).thenReturn(Optional.of(existingBlogPost));
        doNothing().when(blogPostRepository).updateBlogPost(any(BlogPost.class));

        BlogPost updatedBlogPost = blogPostService.updateBlogPost(blogPostId, request.getTitle(), request.getContent());

        assertNotNull(updatedBlogPost);
        assertEquals(request.getTitle(), updatedBlogPost.getTitle());
        assertEquals(request.getContent(), updatedBlogPost.getContent());
        verify(blogPostRepository, times(1)).updateBlogPost(any(BlogPost.class));
    }

    @Test
    void deleteBlogPost_shouldDeleteBlogPostIfExists() {
        UUID blogPostId = UUID.randomUUID();

        doNothing().when(blogPostRepository).deleteBlogPost(blogPostId);

        blogPostService.deleteBlogPost(blogPostId);

        verify(blogPostRepository, times(1)).deleteBlogPost(blogPostId);
    }
}
