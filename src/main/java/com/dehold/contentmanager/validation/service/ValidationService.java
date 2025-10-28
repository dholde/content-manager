package com.dehold.contentmanager.validation.service;

import com.dehold.contentmanager.validation.web.dto.BlogPostValidationRequest;
import com.dehold.contentmanager.validation.web.dto.ValidationResponse;

public interface ValidationService {
    ValidationResponse validateBlogPost(BlogPostValidationRequest request);
}
