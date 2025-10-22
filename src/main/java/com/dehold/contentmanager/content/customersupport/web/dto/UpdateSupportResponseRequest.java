package com.dehold.contentmanager.content.customersupport.web.dto;

import java.util.UUID;

public class UpdateSupportResponseRequest {
    private String text;
    private UUID supportRequest;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getSupportRequest() {
        return supportRequest;
    }

    public void setSupportRequest(UUID supportRequest) {
        this.supportRequest = supportRequest;
    }
}

