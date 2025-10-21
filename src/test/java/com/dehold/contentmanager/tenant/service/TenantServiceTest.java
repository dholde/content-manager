package com.dehold.contentmanager.tenant.service;

import com.dehold.contentmanager.tenant.model.Tenant;
import com.dehold.contentmanager.tenant.repository.TenantRepository;
import com.dehold.contentmanager.tenant.web.dto.CreateTenantRequest;
import com.dehold.contentmanager.tenant.web.dto.UpdateTenantRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TenantServiceTest {

    @Mock
    private TenantRepository tenantRepository;

    @InjectMocks
    private TenantServiceImpl tenantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTenant_shouldCreateAndReturnTenant() {
        CreateTenantRequest request = new CreateTenantRequest();
        request.setName("Test Tenant");
        request.setIdentifier("test-identifier");

        Tenant tenant = new Tenant(UUID.randomUUID(), "Test Tenant", "test-identifier", Instant.now(), Instant.now());

        doNothing().when(tenantRepository).createTenant(any(Tenant.class));

        Tenant createdTenant = tenantService.createTenant(request);

        assertNotNull(createdTenant);
        assertEquals(request.getName(), createdTenant.getName());
        assertEquals(request.getIdentifier(), createdTenant.getIdentifier());
        verify(tenantRepository, times(1)).createTenant(any(Tenant.class));
    }

    @Test
    void getTenant_shouldReturnTenantIfExists() {
        UUID tenantId = UUID.randomUUID();
        Tenant tenant = new Tenant(tenantId, "Test Tenant", "test-identifier", Instant.now(), Instant.now());

        when(tenantRepository.getTenantById(tenantId)).thenReturn(Optional.of(tenant));

        Tenant foundTenant = tenantService.getTenant(tenantId);

        assertNotNull(foundTenant);
        assertEquals(tenantId, foundTenant.getId());
        verify(tenantRepository, times(1)).getTenantById(tenantId);
    }

    @Test
    void updateTenant_shouldUpdateAndReturnUpdatedTenant() {
        UUID tenantId = UUID.randomUUID();
        Tenant existingTenant = new Tenant(tenantId, "Old Name", "old-identifier", Instant.now(), Instant.now());
        UpdateTenantRequest request = new UpdateTenantRequest();
        request.setName("New Name");
        request.setIdentifier("new-identifier");

        when(tenantRepository.getTenantById(tenantId)).thenReturn(Optional.of(existingTenant));
        doNothing().when(tenantRepository).updateTenant(any(Tenant.class));

        Tenant updatedTenant = tenantService.updateTenant(tenantId, request);

        assertNotNull(updatedTenant);
        assertEquals(request.getName(), updatedTenant.getName());
        assertEquals(request.getIdentifier(), updatedTenant.getIdentifier());
        verify(tenantRepository, times(1)).updateTenant(any(Tenant.class));
    }

    @Test
    void deleteTenant_shouldDeleteTenantIfExists() {
        UUID tenantId = UUID.randomUUID();
        Tenant tenant = new Tenant(tenantId, "Test Tenant", "test-identifier", Instant.now(), Instant.now());

        when(tenantRepository.getTenantById(tenantId)).thenReturn(Optional.of(tenant));
        doNothing().when(tenantRepository).deleteTenant(tenantId);

        tenantService.deleteTenant(tenantId);

        verify(tenantRepository, times(1)).deleteTenant(tenantId);
    }
}
