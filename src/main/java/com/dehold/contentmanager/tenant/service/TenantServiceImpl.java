package com.dehold.contentmanager.tenant.service;

import com.dehold.contentmanager.tenant.model.Tenant;
import com.dehold.contentmanager.tenant.repository.TenantRepository;
import com.dehold.contentmanager.tenant.web.dto.CreateTenantRequest;
import com.dehold.contentmanager.tenant.web.dto.UpdateTenantRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Tenant createTenant(CreateTenantRequest dto) {
        Tenant tenant = new Tenant(
                UUID.randomUUID(),
                dto.getAlias(),
                dto.getEmail(),
                Instant.now(),
                Instant.now()
        );
        tenantRepository.createTenant(tenant);
        return tenant;
    }

    @Override
    public Tenant getTenant(UUID id) {
        return tenantRepository.getTenantById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
    }

    @Override
    public Tenant updateTenant(UUID id, UpdateTenantRequest dto) {
        Tenant existingTenant = getTenant(id);
        Tenant updatedTenant = new Tenant(
                existingTenant.getId(),
                dto.getAlias() != null ? dto.getAlias() : existingTenant.getAlias(),
                dto.getEmail() != null ? dto.getEmail() : existingTenant.getEmail(),
                existingTenant.getCreatedAt(),
                Instant.now()
        );
        tenantRepository.updateTenant(updatedTenant);
        return updatedTenant;
    }

    @Override
    public void deleteTenant(UUID id) {
        if (tenantRepository.getTenantById(id).isEmpty()) {
            throw new RuntimeException("Tenant not found");
        }
        tenantRepository.deleteTenant(id);
    }
}
