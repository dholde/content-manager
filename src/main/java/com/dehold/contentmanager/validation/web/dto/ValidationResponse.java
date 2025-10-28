package com.dehold.contentmanager.validation.web.dto;

import java.util.Objects;

public class ValidationResponse {
    private String contentType;
    private ValidationResultDto validationResult;


    public ValidationResponse() {
    }

    public ValidationResponse(String contentType, ValidationResultDto validationResult) {
        this.contentType = contentType;
        this.validationResult = validationResult;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ValidationResultDto getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResultDto validationResult) {
        this.validationResult = validationResult;
    }
}
