package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.validation.model.ForbiddenWords;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.UUID;

public class ForbiddenWordsUpdateDto {
    private UUID id;
    private UUID userId;
    private String description;
    private String contentType;
    private String fieldName;
    private LinkedHashSet<String> words;

    public ForbiddenWordsUpdateDto() {
    }

    public ForbiddenWordsUpdateDto(UUID id, UUID userId, String description, String contentType,
                                   String fieldName, LinkedHashSet<String> words) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.contentType = contentType;
        this.fieldName = fieldName;
        this.words = words;
    }

    public static ForbiddenWordsUpdateDto from(ForbiddenWords forbiddenWords) {
        return new ForbiddenWordsUpdateDto(
                forbiddenWords.getId(),
                forbiddenWords.getUserId(),
                forbiddenWords.getDescription(),
                forbiddenWords.getContentType(),
                forbiddenWords.getFieldName(),
                forbiddenWords.getWords()
        );
    }

    public ForbiddenWords toForbiddenWords() {
        return new ForbiddenWords(
                this.id,
                this.userId,
                this.description,
                this.contentType,
                this.fieldName,
                this.words
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LinkedHashSet<String> getWords() {
        return words;
    }

    public void setWords(LinkedHashSet<String> words) {
        this.words = words;
    }
    
}
