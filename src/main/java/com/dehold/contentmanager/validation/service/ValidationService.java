package com.dehold.contentmanager.validation.service;

import com.dehold.contentmanager.validation.result.ValidationResult;
import com.dehold.contentmanager.validation.web.dto.BlogPostValidationRequest;
import com.dehold.contentmanager.validation.web.dto.ValidationResponse;
import org.springframework.stereotype.Service;

public interface ValidationService {
    ValidationResponse validateBlogPost(BlogPostValidationRequest request);
}
