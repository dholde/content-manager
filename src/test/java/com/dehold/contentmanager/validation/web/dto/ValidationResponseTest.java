package com.dehold.contentmanager.validation.web.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationResponseTest {

    @Test
    void givenEqualValidationResponses_whenEquals_thenReturnTrue() {
        ValidationResultDto validationResult1 = new ValidationResultDto(true, null);
        ValidationResultDto validationResult2 = new ValidationResultDto(true, null);

        ValidationResponse response1 = new ValidationResponse("BlogPost", validationResult1);
        ValidationResponse response2 = new ValidationResponse("BlogPost", validationResult2);

        assertEquals(response1, response2);
    }

    @Test
    void givenDifferentValidationResponses_whenEquals_thenReturnFalse() {
        ValidationResultDto validationResult1 = new ValidationResultDto(true, null);
        ValidationResultDto validationResult2 = new ValidationResultDto(false, null);

        ValidationResponse response1 = new ValidationResponse("BlogPost", validationResult1);
        ValidationResponse response2 = new ValidationResponse("BlogPost", validationResult2);

        assertNotEquals(response1, response2);
    }

    @Test
    void givenDifferentContentTypes_whenEquals_thenReturnFalse() {
        ValidationResultDto validationResult = new ValidationResultDto(true, null);

        ValidationResponse response1 = new ValidationResponse("BlogPost", validationResult);
        ValidationResponse response2 = new ValidationResponse("Article", validationResult);

        assertNotEquals(response1, response2);
    }
}