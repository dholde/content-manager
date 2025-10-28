package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.validation.result.ValidationResult;
import com.dehold.contentmanager.validation.service.ValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validate")
public class ValidationController {
    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping("/blog-post")
    public ResponseEntity<ValidationResult> validateBlogPost(@RequestBody BlogPostValidationRequest request) {
        ValidationResult result = validationService.validateBlogPost(request);
        return ResponseEntity.ok().body(result);
    }
}
