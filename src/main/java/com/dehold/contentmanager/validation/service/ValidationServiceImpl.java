package com.dehold.contentmanager.validation.service;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.validation.pipeline.ValidationPipeline;
import com.dehold.contentmanager.validation.pipeline.ValidationPipelineBuilder;
import com.dehold.contentmanager.validation.model.ValidationResult;
import com.dehold.contentmanager.validation.step.LengthValidator;
import com.dehold.contentmanager.validation.web.dto.BlogPostValidationRequest;
import com.dehold.contentmanager.validation.web.dto.ValidationResponse;
import com.dehold.contentmanager.validation.web.dto.ValidationResultDto;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public ValidationResponse validateBlogPost(BlogPostValidationRequest request) {
        ValidationPipeline<BlogPost> pipeline = new ValidationPipelineBuilder<BlogPost>()
                .addStep(new LengthValidator<>(BlogPost::getTitle, "title", request.getTitleMinLength(), request.getTitleMaxLength()))
                .addStep(new LengthValidator<>(BlogPost::getContent, "content", request.getContentMinLength(), request.getContentMaxLength()))
                .build();
        ValidationResult result = pipeline.run(request.getBlogPost());
        ValidationResultDto resultDto = ValidationResultDto.from(result);
        return new ValidationResponse(BlogPost.class.getSimpleName(), resultDto);
    }
}
