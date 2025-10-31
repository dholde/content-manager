package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.exception.InvalidPayloadException;
import com.dehold.contentmanager.validation.model.ForbiddenWords;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

    public void validateForUpdate() {
        List<String> nullFields = new ArrayList<>();

        if (id == null) nullFields.add("id");
        if (userId == null) nullFields.add("userId");
        if (description == null) nullFields.add("description");
        if (contentType == null) nullFields.add("contentType");
        if (fieldName == null) nullFields.add("fieldName");
        if (words == null) nullFields.add("words");

        if (!nullFields.isEmpty()) {
            throw new InvalidPayloadException("The following fields must not be null: " + String.join(", ", nullFields));
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForbiddenWordsUpdateDto that = (ForbiddenWordsUpdateDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(description, that.description) &&
                Objects.equals(contentType, that.contentType) &&
                Objects.equals(fieldName, that.fieldName) &&
                Objects.equals(words, that.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, description, contentType, fieldName, words);
    }
}
