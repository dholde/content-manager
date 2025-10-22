package com.dehold.contentmanager.tenant.web;

import com.dehold.contentmanager.tenant.model.Tenant;
import com.dehold.contentmanager.tenant.repository.TenantRepository;
import com.dehold.contentmanager.tenant.web.dto.CreateTenantRequest;
import com.dehold.contentmanager.tenant.web.dto.UpdateTenantRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TenantControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TenantRepository tenantRepository;

    @Test
    void createTenant_shouldReturnCreatedTenant() {
        CreateTenantRequest request = new CreateTenantRequest();
        request.setName("Integration Test Tenant");
        request.setIdentifier("integration-test-identifier-" + UUID.randomUUID());

        ResponseEntity<Tenant> response = restTemplate.postForEntity("http://localhost:" + port + "/api/tenants", request, Tenant.class);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(request.getName(), response.getBody().getName());
        assertEquals(request.getIdentifier(), response.getBody().getIdentifier());
    }

    @Test
    void getTenant_shouldReturnTenant() {
        Tenant tenant = new Tenant(UUID.randomUUID(), "Integration Test Tenant", "integration-test-identifier", Instant.now(), Instant.now());
        tenantRepository.createTenant(tenant);

        ResponseEntity<Tenant> response = restTemplate.getForEntity("http://localhost:" + port + "/api/tenants/" + tenant.getId(), Tenant.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(tenant.getId(), response.getBody().getId());
    }

    @Test
    void updateTenant_shouldReturnUpdatedTenant() {
        Tenant tenant = new Tenant(UUID.randomUUID(), "Old Name", "old-identifier", Instant.now(), Instant.now());
        tenantRepository.createTenant(tenant);

        UpdateTenantRequest request = new UpdateTenantRequest();
        request.setName("Updated Name");
        request.setIdentifier("updated-identifier");

        HttpEntity<UpdateTenantRequest> entity = new HttpEntity<>(request);
        ResponseEntity<Tenant> response = restTemplate.exchange("http://localhost:" + port + "/api/tenants/" + tenant.getId(), HttpMethod.PUT, entity, Tenant.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(request.getName(), response.getBody().getName());
        assertEquals(request.getIdentifier(), response.getBody().getIdentifier());
    }

    @Test
    void deleteTenant_shouldDeleteTenant() {
        CreateTenantRequest createRequest = new CreateTenantRequest();
        createRequest.setName("Integration Test Tenant");
        createRequest.setIdentifier("integration-test-identifier-" + UUID.randomUUID());

        ResponseEntity<Tenant> createResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/tenants", createRequest, Tenant.class);
        assertEquals(201, createResponse.getStatusCode().value());
        assertNotNull(createResponse.getBody());

        UUID tenantId = createResponse.getBody().getId();

        restTemplate.delete("http://localhost:" + port + "/api/tenants/" + tenantId);

        ResponseEntity<Tenant> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/api/tenants/" + tenantId, Tenant.class);
        assertEquals(500, getResponse.getStatusCode().value()); //TODO: Fix this to return 404 Not Found
    }
}
