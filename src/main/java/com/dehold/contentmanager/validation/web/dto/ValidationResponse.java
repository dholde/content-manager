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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        ValidationResponse that = (ValidationResponse) o;
        return contentType.equals(((ValidationResponse) o).contentType) && validationResult.isValid() == that.validationResult.isValid() &&
               validationResult.getErrors().equals(that.validationResult.getErrors());
    }
}
