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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ValidationResponse that = (ValidationResponse) o;
        return Objects.equals(contentType, that.contentType) && Objects.equals(validationResult, that.validationResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentType, validationResult);
    }
}
