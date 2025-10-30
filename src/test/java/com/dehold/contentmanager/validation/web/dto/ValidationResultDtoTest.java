package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.validation.model.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationResultDtoTest {

    @Test
    void givenEqualValidationResultDtosWithNullError_whenEquals_thenReturnTrue() {
        String contentType = BlogPost.class.getSimpleName();
        UUID contentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ValidationResultDto dto1 = new ValidationResultDto(contentType, contentId, userId, true, null);
        ValidationResultDto dto2 = new ValidationResultDto(contentType, contentId, userId, true, null);

        assertEquals(dto1, dto2);
    }

    @Test
    void givenDifferentValidationResultDtosWithNullError_whenEquals_thenReturnFalse() {
        String contentType = BlogPost.class.getSimpleName();
        UUID contentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ValidationResultDto dto1 = new ValidationResultDto(contentType, contentId, userId, true, null);
        ValidationResultDto dto2 = new ValidationResultDto(contentType, contentId, userId, false, null);

        assertNotEquals(dto1, dto2);
    }

    @Test
    void givenEqualValidationResultDtosWithErrors_whenEquals_thenReturnTrue() {
        String contentType = BlogPost.class.getSimpleName();
        UUID contentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ValidationError error1 = new ValidationError("FIELD_1", "Error message 1");
        ValidationError error2 = new ValidationError("FIELD_2", "Error message 2");

        ValidationResultDto dto1 = new ValidationResultDto(contentType, contentId, userId, false, List.of(error1,
                error2));
        ValidationResultDto dto2 = new ValidationResultDto(contentType, contentId, userId, false, List.of(error1,
                error2));

        assertEquals(dto1, dto2);
    }

    @Test
    void givenDifferentValidationResultDtosWithErrors_whenEquals_thenReturnFalse() {
        String contentType = BlogPost.class.getSimpleName();
        UUID contentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ValidationError error1 = new ValidationError("FIELD_1", "Error message 1");
        ValidationError error2 = new ValidationError("FIELD_2", "Error message 2");
        ValidationError error3 = new ValidationError("FIELD_3", "Error message 3");

        ValidationResultDto dto1 = new ValidationResultDto(contentType, contentId, userId, false, List.of(error1,
                error2));
        ValidationResultDto dto2 = new ValidationResultDto(contentType, contentId, userId, false, List.of(error1,
                error3));

        assertNotEquals(dto1, dto2);
    }

}