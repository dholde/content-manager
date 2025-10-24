package com.dehold.contentmanager.validation.step;

import com.dehold.contentmanager.content.customersupport.model.SupportRequest;
import com.dehold.contentmanager.validation.result.ValidationError;
import com.dehold.contentmanager.validation.result.ValidationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberForbiddenValidatorTest {

    private final PhoneNumberForbiddenValidator<SupportRequest> validator =
            new PhoneNumberForbiddenValidator<>(SupportRequest::getText, "text");

    @Test
    void validate_withPhoneNumber_returnsFalse() {
        SupportRequest request = new SupportRequest();
        request.setText("This contains a phone number: +1234567890");
        ValidationResult result = validator.validate(request);
        assertFalse(result.isValid());
        List<ValidationError> errors = result.getErrors();
        assertEquals(1, errors.size());
        assertEquals(PhoneNumberForbiddenValidator.ERROR_CODE, errors.getFirst().code());
        assertEquals(PhoneNumberForbiddenValidator.errorMessagePhoneNumberForbidden(validator.getFieldName()),
                errors.getFirst().message());
    }

    @Test
    void validate_withoutPhoneNumber_returnsTrue() {
        SupportRequest request = new SupportRequest();
        request.setText("This content is clean and has no phone numbers.");
        ValidationResult result = validator.validate(request);
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void validate_emptyContent_returnsTrue() {
        SupportRequest request = new SupportRequest();
        request.setText("");
        ValidationResult result = validator.validate(request);
        assertTrue(result.isValid());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void error_message_phone_number_forbidden() {
        String expectedMessage = "The field 'text' contains a phone number, which is not allowed.";
        assertEquals(expectedMessage, PhoneNumberForbiddenValidator.errorMessagePhoneNumberForbidden("text"));
    }

}