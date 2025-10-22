package com.dehold.contentmanager.validation.step;

public interface ValidationStep {
    public boolean validate(String value, int minLength, int maxLength);
}
