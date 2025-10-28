package com.dehold.contentmanager.validation.service;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.validation.pipeline.ValidationPipeline;
import com.dehold.contentmanager.validation.pipeline.ValidationPipelineBuilder;
import com.dehold.contentmanager.validation.result.ValidationResult;
import com.dehold.contentmanager.validation.step.LengthValidator;
import com.dehold.contentmanager.validation.web.dto.BlogPostValidationRequest;

public class ValidationServiceImpl implements ValidationService {
    @Override
    public ValidationResult validateBlogPost(BlogPostValidationRequest request) {
        ValidationPipeline<BlogPost> pipeline = new ValidationPipelineBuilder<BlogPost>()
                .addStep(new LengthValidator<>(BlogPost::getTitle, "title", request.getTitleMinLength(), request.getTitleMaxLength()))
                .addStep(new LengthValidator<>(BlogPost::getContent, "content", request.getContentMinLength(), request.getContentMaxLength()))
                .build();
        return pipeline.run(request.getBlogPost());
    }
}
