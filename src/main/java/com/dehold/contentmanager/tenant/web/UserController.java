package com.dehold.contentmanager.tenant.web;

import com.dehold.contentmanager.tenant.service.UserService;
import com.dehold.contentmanager.tenant.web.dto.CreateUserRequest;
import com.dehold.contentmanager.tenant.web.dto.UserResponse;
import com.dehold.contentmanager.tenant.web.dto.UpdateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
