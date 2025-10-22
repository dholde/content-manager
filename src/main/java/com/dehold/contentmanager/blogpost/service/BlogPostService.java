package com.dehold.contentmanager.blogpost.service;

import com.dehold.contentmanager.blogpost.model.BlogPost;
import com.dehold.contentmanager.blogpost.repository.BlogPostRepository;
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

    public BlogPost createBlogPost(String title, String content) {
        BlogPost blogPost = new BlogPost(
                UUID.randomUUID(),
                title,
                content,
                Instant.now(),
                Instant.now()
        );
        blogPostRepository.createBlogPost(blogPost);
        return blogPost;
    }

    public BlogPost getBlogPost(UUID id) {
        return blogPostRepository.getBlogPost(id);
    }

    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.getAllBlogPosts();
    }

    public BlogPost updateBlogPost(UUID id, String title, String content) {
        BlogPost blogPost = blogPostRepository.getBlogPost(id);
        blogPost.setTitle(title);
        blogPost.setContent(content);
        blogPost.setUpdatedAt(Instant.now());
        blogPostRepository.updateBlogPost(blogPost);
        return blogPost;
    }

    public void deleteBlogPost(UUID id) {
        blogPostRepository.deleteBlogPost(id);
    }
}
