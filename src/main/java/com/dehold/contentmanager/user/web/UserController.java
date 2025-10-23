package com.dehold.contentmanager.user.web;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.content.blogpost.service.BlogPostService;
import com.dehold.contentmanager.user.service.UserService;
import com.dehold.contentmanager.user.web.dto.CreateUserRequest;
import com.dehold.contentmanager.user.web.dto.UserResponse;
import com.dehold.contentmanager.user.web.dto.UpdateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;
    private final BlogPostService blogPostService;

    public UserController(UserService userService, BlogPostService blogPostService){
        this.userService = userService;
        this.blogPostService = blogPostService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        var user = userService.createUser(request);
        var response = new UserResponse(
                user.getId(),
                user.getAlias(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {
        var user = userService.getUser(id);
        var response = new UserResponse(
                user.getId(),
                user.getAlias(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/blogposts")
    public ResponseEntity<List<BlogPost>> getBlogpostsByUserId(@PathVariable UUID id) {
        List<BlogPost> blogPosts = this.blogPostService.getBlogPostsByUserId(id);
        return ResponseEntity.ok(blogPosts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
        var user = userService.updateUser(id, request);
        var response = new UserResponse(
                user.getId(),
                user.getAlias(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
