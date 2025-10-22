package com.dehold.contentmanager.validation.step;

public class LengthValidator implements ValidationStep {

    private int minLength;
    private int maxLength;

    public LengthValidator(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public boolean validate(String content) {
        return this.validate(content, minLength, maxLength);
    }

    private boolean validate(String value, int minLength, int maxLength) {
        if (minLength < 0 || maxLength < 0 || minLength > maxLength) {
            throw new IllegalArgumentException("Invalid min/max lengths");
        }
        if (value == null) {
            return false;
        }
        int len = value.length();
        return len >= minLength && len <= maxLength;
    }
}
