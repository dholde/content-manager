package com.dehold.contentmanager.tenant.web;

import com.dehold.contentmanager.tenant.model.Tenant;
import com.dehold.contentmanager.tenant.repository.TenantRepository;
import com.dehold.contentmanager.tenant.web.dto.CreateTenantRequest;
import com.dehold.contentmanager.tenant.web.dto.UpdateTenantRequest;
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
        request.setAlias("Integration Test Tenant");
        request.setEmail("integration-test-" + UUID.randomUUID() + "@example.com");

        ResponseEntity<Tenant> response = restTemplate.postForEntity("http://localhost:" + port + "/api/tenants", request, Tenant.class);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(request.getAlias(), response.getBody().getAlias());
        assertEquals(request.getEmail(), response.getBody().getEmail());
    }

    @Test
    void getTenant_shouldReturnTenant() {
        Tenant tenant = new Tenant(UUID.randomUUID(), "Integration Test Tenant", "integration@example.com", Instant.now(), Instant.now());
        tenantRepository.createTenant(tenant);

        ResponseEntity<Tenant> response = restTemplate.getForEntity("http://localhost:" + port + "/api/tenants/" + tenant.getId(), Tenant.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(tenant.getId(), response.getBody().getId());
    }

    @Test
    void updateTenant_shouldReturnUpdatedTenant() {
        Tenant tenant = new Tenant(UUID.randomUUID(), "Old Name", "old@example.com", Instant.now(), Instant.now());
        tenantRepository.createTenant(tenant);

        UpdateTenantRequest request = new UpdateTenantRequest();
        request.setAlias("Updated Name");
        request.setEmail("updated@example.com");

        HttpEntity<UpdateTenantRequest> entity = new HttpEntity<>(request);
        ResponseEntity<Tenant> response = restTemplate.exchange("http://localhost:" + port + "/api/tenants/" + tenant.getId(), HttpMethod.PUT, entity, Tenant.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(request.getAlias(), response.getBody().getAlias());
        assertEquals(request.getEmail(), response.getBody().getEmail());
    }

    @Test
    void deleteTenant_shouldDeleteTenant() {
        CreateTenantRequest createRequest = new CreateTenantRequest();
        createRequest.setAlias("Integration Test Tenant");
        createRequest.setEmail("integration-test-" + UUID.randomUUID() + "@example.com");

        ResponseEntity<Tenant> createResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/tenants", createRequest, Tenant.class);
        assertEquals(201, createResponse.getStatusCode().value());
        assertNotNull(createResponse.getBody());

        UUID tenantId = createResponse.getBody().getId();

        restTemplate.delete("http://localhost:" + port + "/api/tenants/" + tenantId);

        ResponseEntity<Tenant> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/api/tenants/" + tenantId, Tenant.class);
        assertEquals(500, getResponse.getStatusCode().value());
    }
}
