package com.dehold.contentmanager.tenant.service;

import com.dehold.contentmanager.tenant.model.Tenant;
import com.dehold.contentmanager.tenant.web.dto.CreateTenantRequest;
import com.dehold.contentmanager.tenant.web.dto.UpdateTenantRequest;

import java.util.UUID;

public interface TenantService {

    Tenant createTenant(CreateTenantRequest dto);

    Tenant getTenant(UUID id);

    Tenant updateTenant(UUID id, UpdateTenantRequest dto);

    void deleteTenant(UUID id);
}
