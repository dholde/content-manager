package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.validation.result.ValidationError;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationResponseTest {

    @Test
    void givenEqualValidationResponses_whenEquals_thenReturnTrue() {
        ValidationError error1 = new ValidationError("FIELD_1", "Error message 1");
        ValidationError error2 = new ValidationError("FIELD_2", "Error message 2");

        ValidationResultDto validationResult1 = new ValidationResultDto(true, List.of(error1, error2));
        ValidationResultDto validationResult2 = new ValidationResultDto(true, List.of(error1, error2));

        ValidationResponse response1 = new ValidationResponse("BlogPost", validationResult1);
        ValidationResponse response2 = new ValidationResponse("BlogPost", validationResult2);

        assertEquals(response1, response2);
    }

    @Test
    void givenDifferentValidationResponses_whenEquals_thenReturnFalse() {
        ValidationError error1 = new ValidationError("FIELD_1", "Error message 1");
        ValidationError error2 = new ValidationError("FIELD_2", "Error message 2");

        ValidationResultDto validationResult1 = new ValidationResultDto(true, List.of(error1, error2));
        ValidationResultDto validationResult2 = new ValidationResultDto(false, List.of(error1));

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