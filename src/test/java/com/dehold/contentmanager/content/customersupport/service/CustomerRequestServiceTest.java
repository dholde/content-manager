package com.dehold.contentmanager.content.customersupport.service;

import com.dehold.contentmanager.content.customersupport.model.CustomerRequest;
import com.dehold.contentmanager.content.customersupport.repository.CustomerRequestRepository;
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

class CustomerRequestServiceTest {

    @Mock
    private CustomerRequestRepository repository;

    @InjectMocks
    private CustomerRequestService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldCallRepositoryCreate() {
        CustomerRequest request = new CustomerRequest(
            UUID.randomUUID(),
            "Test text",
            UUID.randomUUID(),
            UUID.randomUUID(),
            Instant.now(),
            Instant.now()
        );

        service.save(request);

        verify(repository, times(1)).create(request);
    }

    @Test
    void findById_shouldReturnCustomerRequest() {
        UUID id = UUID.randomUUID();
        CustomerRequest request = new CustomerRequest(
            id,
            "Test text",
            UUID.randomUUID(),
            UUID.randomUUID(),
            Instant.now(),
            Instant.now()
        );
        when(repository.getById(id)).thenReturn(Optional.of(request));

        CustomerRequest result = service.findById(id);

        assertEquals(request, result);
        verify(repository, times(1)).getById(id);
    }

    @Test
    void deleteById_shouldCallRepositoryDeleteById() {
        UUID id = UUID.randomUUID();

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }
}
