package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;

public class BlogPostValidationRequest {
    int titleMinLength;
    int titleMaxLength;
    int contentMinLength;
    int contentMaxLength;
    BlogPost blogPost;


    public int getTitleMinLength() {
        return titleMinLength;
    }
    public void setTitleMinLength(int titleMinLength) {
        this.titleMinLength = titleMinLength;
    }
    public int getTitleMaxLength() {
        return titleMaxLength;
    }
    public void setTitleMaxLength(int titleMaxLength) {
        this.titleMaxLength = titleMaxLength;
    }
    public int getContentMinLength() {
        return contentMinLength;
    }
    public void setContentMinLength(int contentMinLength) {
        this.contentMinLength = contentMinLength;
    }
    public int getContentMaxLength() {
        return contentMaxLength;
    }
    public void setContentMaxLength(int contentMaxLength) {
        this.contentMaxLength = contentMaxLength;
    }
    public BlogPost getBlogPost() {
        return blogPost;
    }
}
