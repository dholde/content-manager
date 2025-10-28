package com.dehold.contentmanager.validation.web;

import com.dehold.contentmanager.validation.service.ValidationService;
import com.dehold.contentmanager.validation.web.dto.BlogPostValidationRequest;
import com.dehold.contentmanager.validation.web.dto.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validate")
public class ValidationController {
    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/blog-post")
    public ResponseEntity<ValidationResponse> validateBlogPost(@RequestBody BlogPostValidationRequest request) {
        ValidationResponse result = validationService.validateBlogPost(request);
        return ResponseEntity.ok(result);
    }
}
