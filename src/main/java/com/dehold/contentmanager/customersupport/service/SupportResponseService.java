package com.dehold.contentmanager.customersupport.service;

import com.dehold.contentmanager.customersupport.model.SupportResponse;
import java.util.Optional;
import java.util.UUID;

public interface SupportResponseService {
    void createSupportResponse(SupportResponse response);
    Optional<SupportResponse> getSupportResponse(UUID id);
    void updateSupportResponse(SupportResponse response);
    void deleteSupportResponse(UUID id);
}

