package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.content.customersupport.model.SupportRequest;
import com.dehold.contentmanager.validation.model.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationResponseTest {

    @Test
    void givenEqualValidationResponses_whenEquals_thenReturnTrue() {
        ValidationError error1 = new ValidationError("FIELD_1", "Error message 1");
        ValidationError error2 = new ValidationError("FIELD_2", "Error message 2");

        String contentType = BlogPost.class.getSimpleName();
        UUID contentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ValidationResultDto validationResult1 = new ValidationResultDto(contentType, contentId, userId, true,
                List.of(error1,
                error2));
        ValidationResultDto validationResult2 = new ValidationResultDto(contentType, contentId, userId, true,
                List.of(error1,
                error2));

        ValidationResponse response1 = new ValidationResponse("BlogPost", validationResult1);
        ValidationResponse response2 = new ValidationResponse("BlogPost", validationResult2);

        assertEquals(response1, response2);
    }

    @Test
    void givenDifferentValidationResponses_whenEquals_thenReturnFalse() {
        ValidationError error1 = new ValidationError("FIELD_1", "Error message 1");
        ValidationError error2 = new ValidationError("FIELD_2", "Error message 2");

        String contentType = BlogPost.class.getSimpleName();
        UUID contentId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ValidationResultDto validationResult1 = new ValidationResultDto(contentType, contentId, userId, true,
                List.of(error1,
                error2));
        ValidationResultDto validationResult2 = new ValidationResultDto(contentType, contentId, userId, false,
                List.of(error1));

        ValidationResponse response1 = new ValidationResponse("BlogPost", validationResult1);
        ValidationResponse response2 = new ValidationResponse("BlogPost", validationResult2);

        assertNotEquals(response1, response2);
    }

    @Test
    void givenDifferentContentTypes_whenEquals_thenReturnFalse() {
        String contentType1 = BlogPost.class.getSimpleName();
        String contentType2 = SupportRequest.class.getSimpleName();
        UUID contentId1 = UUID.randomUUID();
        UUID contentId2 = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ValidationResultDto validationResult1 = new ValidationResultDto(contentType1, contentId1, userId, true, null);
        ValidationResultDto validationResult2 = new ValidationResultDto(contentType2, contentId2, userId, true, null);

        ValidationResponse response1 = new ValidationResponse("BlogPost", validationResult1);
        ValidationResponse response2 = new ValidationResponse("Article", validationResult2);

        assertNotEquals(response1, response2);
    }
}