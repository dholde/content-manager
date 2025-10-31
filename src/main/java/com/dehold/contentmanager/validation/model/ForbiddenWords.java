package com.dehold.contentmanager.validation.model;

import java.util.Set;
import java.util.UUID;

public class ForbiddenWords {
    UUID id;
    UUID userId;
    String contentType;
    String fieldName;
    Set<String> words;

    public ForbiddenWords(UUID id, UUID userId, String contentType, String fieldName, Set<String> words) {
        this.id = id;
        this.userId = userId;
        this.contentType = contentType;
        this.fieldName = fieldName;
        this.words = words;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Set<String> getWords() {
        return words;
    }

    public void setWords(Set<String> words) {
        this.words = words;
    }
}
