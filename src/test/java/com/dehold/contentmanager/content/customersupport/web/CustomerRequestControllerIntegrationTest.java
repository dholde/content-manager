package com.dehold.contentmanager.content.customersupport.web;

import com.dehold.contentmanager.content.customersupport.model.CustomerRequest;
import com.dehold.contentmanager.content.customersupport.repository.CustomerRequestRepository;
import com.dehold.contentmanager.content.customersupport.web.dto.CustomerRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerRequestControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRequestRepository repository;

    @Test
    void createCustomerRequest_shouldReturnCreated() {
        CustomerRequestDto request = new CustomerRequestDto();
        request.setText("Customer request text");
        request.setSupportResponse(UUID.randomUUID());
        request.setCustomerId(UUID.randomUUID());
        request.setCreatedAt(Instant.now());
        request.setUpdatedAt(Instant.now());

        ResponseEntity<CustomerRequest> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/customer-requests",
            request,
            CustomerRequest.class
        );

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(request.getText(), response.getBody().getText());
    }

    @Test
    void getCustomerRequest_shouldReturnRequest() {
        CustomerRequest request = new CustomerRequest(
            UUID.randomUUID(),
            "Test text",
            UUID.randomUUID(),
            UUID.randomUUID(),
            Instant.now(),
            Instant.now()
        );
        repository.create(request);

        ResponseEntity<CustomerRequest> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/customer-requests/" + request.getId(),
            CustomerRequest.class
        );

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(request.getText(), response.getBody().getText());
    }

    @Test
    void updateCustomerRequest_shouldReturnUpdated() {
        CustomerRequest request = new CustomerRequest(
            UUID.randomUUID(),
            "Old text",
            UUID.randomUUID(),
            UUID.randomUUID(),
            Instant.now(),
            Instant.now()
        );
        repository.create(request);

        CustomerRequestDto updateRequest = new CustomerRequestDto();
        updateRequest.setText("Updated text");
        updateRequest.setSupportResponse(request.getSupportResponse());
        updateRequest.setCustomerId(request.getCustomerId());
        updateRequest.setCreatedAt(request.getCreatedAt());
        updateRequest.setUpdatedAt(Instant.now());

        HttpEntity<CustomerRequestDto> entity = new HttpEntity<>(updateRequest);
        ResponseEntity<CustomerRequest> response = restTemplate.exchange(
            "http://localhost:" + port + "/api/customer-requests/" + request.getId(),
            HttpMethod.PUT,
            entity,
            CustomerRequest.class
        );

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(updateRequest.getText(), response.getBody().getText());
    }

    @Test
    void deleteCustomerRequest_shouldReturnNoContent() {
        CustomerRequest request = new CustomerRequest(
            UUID.randomUUID(),
            "Test text",
            UUID.randomUUID(),
            UUID.randomUUID(),
            Instant.now(),
            Instant.now()
        );
        repository.create(request);

        restTemplate.delete("http://localhost:" + port + "/api/customer-requests/" + request.getId());

        Optional<CustomerRequest> deletedRequest = repository.getById(request.getId());
        assertTrue(deletedRequest.isEmpty());
    }
}
