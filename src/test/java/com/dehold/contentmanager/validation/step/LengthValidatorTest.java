package com.dehold.contentmanager.validation.step;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LengthValidatorTest {

    private final LengthValidator validator = new LengthValidator(2, 5);

    @Test
    void validate_withinBounds_returnsTrue() {
        assertTrue(validator.validate("hello"));
    }

    @Test
    void validate_tooShort_returnsFalse() {
        assertFalse(validator.validate("a"));
    }

    @Test
    void validate_tooLong_returnsFalse() {
        assertFalse(validator.validate("abcdef"));
    }

    @Test
    void validate_null_returnsFalse() {
        assertFalse(validator.validate(null));
    }

    @Test
    void validate_minEqualsMax() {
        assertTrue(validator.validate("ab"));
        assertFalse(validator.validate("a"));
    }

}