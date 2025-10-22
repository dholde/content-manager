package com.dehold.contentmanager.validation.step;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LengthValidatorTest implements ValidationStep {

    private final LengthValidator validator = new LengthValidator();

    @Test
    void validate_withinBounds_returnsTrue() {
        assertTrue(validator.validate("hello", 1, 10));
    }

    @Test
    void validate_tooShort_returnsFalse() {
        assertFalse(validator.validate("a", 2, 5));
    }

    @Test
    void validate_tooLong_returnsFalse() {
        assertFalse(validator.validate("abcdef", 1, 5));
    }

    @Test
    void validate_null_returnsFalse() {
        assertFalse(validator.validate(null, 0, 5));
    }

    @Test
    void validate_invalidBounds_throws() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate("x", -1, 5));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("x", 5, 4));
    }

    @Test
    void validate_minEqualsMax() {
        assertTrue(validator.validate("ab", 2, 2));
        assertFalse(validator.validate("a", 2, 2));
    }

}