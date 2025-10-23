package com.dehold.contentmanager.validation.step;

import com.dehold.contentmanager.validation.result.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LengthValidatorTest {

    private final LengthValidator validator = new LengthValidator(2, 5);

    @Test
    void validate_withinBounds_returnsTrue() {
        assertTrue(validator.validate("hello").isValid());
    }

    @Test
    void validate_tooShort_returnsFalse() {
        assertFalse(validator.validate("a").isValid());
    }

    @Test
    void validate_tooShort_returnsErrorCodeAndErrorMessage() {
        List<ValidationError> errors = validator.validate("a").getErrors();
        assertEquals(1, errors.size());
        assertEquals(LengthValidator.ERROR_MESSAGE_TOO_SHORT, errors.getFirst().message());
    }

    @Test
    void validate_tooLong_returnsFalse() {
        assertFalse(validator.validate("abcdef").isValid());
    }

    @Test
    void validate_null_returnsFalse() {
        assertFalse(validator.validate(null).isValid());
    }

    @Test
    void validate_minEqualsMax() {
        assertTrue(validator.validate("ab").isValid());
        assertFalse(validator.validate("a").isValid());
    }

}