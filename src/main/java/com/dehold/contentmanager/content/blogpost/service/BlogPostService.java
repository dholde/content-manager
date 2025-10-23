package com.dehold.contentmanager.content.blogpost.service;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.content.blogpost.repository.BlogPostRepository;
import com.dehold.contentmanager.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public BlogPost createBlogPost(String title, String content, UUID userId) {
        BlogPost blogPost = new BlogPost(
                UUID.randomUUID(),
                title,
                content,
                Instant.now(),
                Instant.now(),
                userId
        );
        blogPostRepository.createBlogPost(blogPost);
        return blogPost;
    }

    public BlogPost getBlogPost(UUID id) {
        return blogPostRepository.getBlogPost(id)
                .orElseThrow(() -> EntityNotFoundException.of("BlogPost", id.toString()));
    }

    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.getAllBlogPosts();
    }

    public BlogPost updateBlogPost(UUID id, String title, String content) {
        BlogPost blogPost = getBlogPost(id); // This will now throw EntityNotFoundException if not found
        blogPost.setTitle(title);
        blogPost.setContent(content);
        blogPost.setUpdatedAt(Instant.now());
        blogPostRepository.updateBlogPost(blogPost);
        return blogPost;
    }

    public void deleteBlogPost(UUID id) {
        blogPostRepository.deleteBlogPost(id);
    }

    public List<BlogPost> getBlogPostsByUserId(UUID userId) {
        return blogPostRepository.getBlogPostsByUserId(userId);
    }
}
