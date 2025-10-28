package com.dehold.contentmanager.validation.web.dto;

import com.dehold.contentmanager.validation.result.ValidationResult;

import java.util.List;

public class ValidationResponse {
    String contentType;
    ValidationResult validationResult;

    public ValidationResponse(String contentType, ValidationResult validationResult) {
        this.contentType = contentType;
        this.validationResult = validationResult;
    }
}
