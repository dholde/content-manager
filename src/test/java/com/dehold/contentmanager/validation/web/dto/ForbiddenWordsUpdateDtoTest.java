package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.exception.InvalidPayloadException;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ForbiddenWordsUpdateDtoTest {

    @Test
    void validateForUpdate_shouldPassWhenAllFieldsArePresent() {
        LinkedHashSet<String> words = new LinkedHashSet<>();
        words.add("test");

        ForbiddenWordsUpdateDto dto = new ForbiddenWordsUpdateDto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Test description",
                "blogpost",
                "content",
                words
        );

        assertDoesNotThrow(dto::validateForUpdate);
    }

    @Test
    void validateForUpdate_shouldThrowExceptionWhenIdIsNull() {
        LinkedHashSet<String> words = new LinkedHashSet<>();
        words.add("test");

        ForbiddenWordsUpdateDto dto = new ForbiddenWordsUpdateDto(
                null, // id is null
                UUID.randomUUID(),
                "Test description",
                "blogpost",
                "content",
                words
        );

        InvalidPayloadException exception = assertThrows(InvalidPayloadException.class, dto::validateForUpdate);
        assertEquals("The following fields must not be null: id", exception.getMessage());
    }

    @Test
    void validateForUpdate_shouldThrowExceptionWithMultipleNullFields() {
        ForbiddenWordsUpdateDto dto = new ForbiddenWordsUpdateDto(
                null, // id is null
                null, // userId is null
                "Test description",
                null, // contentType is null
                "content",
                null  // words is null
        );

        InvalidPayloadException exception = assertThrows(InvalidPayloadException.class, dto::validateForUpdate);
        assertEquals("The following fields must not be null: id, userId, contentType, words", exception.getMessage());
    }

}
