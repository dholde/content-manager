package com.dehold.contentmanager.validation.pipeline;

public interface ValidationPipeline {
    boolean runPipeline(String value, int minLength, int maxLength);
}

