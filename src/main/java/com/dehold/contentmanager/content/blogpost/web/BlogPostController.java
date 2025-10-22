package com.dehold.contentmanager.content.blogpost.web;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.content.blogpost.service.BlogPostService;
import com.dehold.contentmanager.content.blogpost.web.dto.CreateBlogPostRequest;
import com.dehold.contentmanager.content.blogpost.web.dto.UpdateBlogPostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/blogposts")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody CreateBlogPostRequest request) {
        BlogPost blogPost = blogPostService.createBlogPost(request.getTitle(), request.getContent(),
                request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(blogPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPost(@PathVariable UUID id) {
        BlogPost blogPost = blogPostService.getBlogPost(id);
        return ResponseEntity.ok(blogPost);
    }

    @GetMapping
    public ResponseEntity<List<BlogPost>> getAllBlogPosts() {
        List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
        return ResponseEntity.ok(blogPosts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable UUID id, @RequestBody UpdateBlogPostRequest request) {
        BlogPost blogPost = blogPostService.updateBlogPost(id, request.getTitle(), request.getContent());
        return ResponseEntity.ok(blogPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable UUID id) {
        blogPostService.deleteBlogPost(id);
        return ResponseEntity.noContent().build();
    }
}
