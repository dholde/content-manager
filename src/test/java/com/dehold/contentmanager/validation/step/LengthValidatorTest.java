package com.dehold.contentmanager.validation.step;

import com.dehold.contentmanager.content.blogpost.model.BlogPost;
import com.dehold.contentmanager.validation.model.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LengthValidatorTest {

    private final LengthValidator<BlogPost> blogPostContentLengthValidator =
            new LengthValidator<BlogPost>(BlogPost::getContent,"content", 10,13);

    @Test
    void validate_withinBounds_returnsTrue() {
        BlogPost post = new BlogPost(null, "Title", "Within Bounds", null, null, null);
        assertTrue(blogPostContentLengthValidator.validate(post).isValid());
    }

    @Test
    void validate_tooShort_returnsFalse() {
        BlogPost post = new BlogPost(null, "Title", "Too Short", null, null, null);
        assertFalse(blogPostContentLengthValidator.validate(post).isValid());
    }

    @Test
    void validate_tooShort_returnsErrorCodeAndErrorMessage() {
        BlogPost post = new BlogPost(null, "Title", "Too Short", null, null, null);
        List<ValidationError> errors = blogPostContentLengthValidator.validate(post).getErrors();
        assertEquals(1, errors.size());
        assertEquals(LengthValidator.errorMessageTooShort(blogPostContentLengthValidator.getFieldName()),
                errors.getFirst().message());
    }

    @Test
    void validate_tooLong_returnsFalse() {
        BlogPost post = new BlogPost(null, "Title", "This is way too long.", null, null, null);
        assertFalse(blogPostContentLengthValidator.validate(post).isValid());
    }

    @Test
    void validate_tooLong_returnsErrorCodeAndErrorMessage() {
        BlogPost post = new BlogPost(null, "Title", "This is way too long", null, null, null);
        List<ValidationError> errors = blogPostContentLengthValidator.validate(post).getErrors();
        assertEquals(1, errors.size());
        assertEquals(LengthValidator.errorMessageTooShort(blogPostContentLengthValidator.getFieldName()),
                errors.getFirst().message());
    }

    @Test
    void validate_minEqualsMax() {
        LengthValidator<BlogPost> blogPostContentLengthValidator =
                new LengthValidator<BlogPost>(BlogPost::getContent,"content", 10,10);
        BlogPost post = new BlogPost(null, "Title", "min == max", null, null, null);
        assertTrue(blogPostContentLengthValidator.validate(post).isValid());
    }

    @Test
    void error_message_too_short_contains_field_name() {
        String fieldName = "testField";
        assertTrue(LengthValidator.errorMessageTooShort(fieldName).contains(fieldName));
    }

    @Test
    void error_message_too_long_contains_field_name() {
        String fieldName = "testField";
        String expectedMessage = "The field '" + fieldName + "' is too long.";
        assertTrue(LengthValidator.errorMessageTooLong(fieldName).contains(fieldName));
    }

}