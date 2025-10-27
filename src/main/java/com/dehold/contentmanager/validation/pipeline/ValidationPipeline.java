package com.dehold.contentmanager.validation.pipeline;

import com.dehold.contentmanager.validation.step.ValidationStep;

import java.util.List;

public interface ValidationPipeline<T> {
    boolean run(T content);
}
