package com.dehold.contentmanager.content.customersupport.service;

import com.dehold.contentmanager.content.customersupport.model.SupportResponse;
import com.dehold.contentmanager.content.customersupport.repository.SupportResponseRepository;
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

class SupportResponseServiceTest {
    @Mock
    private SupportResponseRepository repository;

    @InjectMocks
    private SupportResponseServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSupportResponse_shouldCallRepository() {
        SupportResponse response = new SupportResponse(UUID.randomUUID(), "Test text", UUID.randomUUID(), Instant.now(), Instant.now());
        doNothing().when(repository).create(any(SupportResponse.class));
        service.createSupportResponse(response);
        verify(repository, times(1)).create(any(SupportResponse.class));
    }

    @Test
    void getSupportResponse_shouldReturnResponseIfExists() {
        UUID id = UUID.randomUUID();
        SupportResponse response = new SupportResponse(id, "Test text", UUID.randomUUID(), Instant.now(), Instant.now());
        when(repository.getById(id)).thenReturn(Optional.of(response));
        SupportResponse found = service.getSupportResponse(id);
        assertEquals(response.getId(), found.getId());
        verify(repository, times(1)).getById(id);
    }

    @Test
    void updateSupportResponse_shouldCallRepository() {
        SupportResponse response = new SupportResponse(UUID.randomUUID(), "Updated text", UUID.randomUUID(), Instant.now(), Instant.now());
        doNothing().when(repository).update(any(SupportResponse.class));
        service.updateSupportResponse(response);
        verify(repository, times(1)).update(any(SupportResponse.class));
    }

    @Test
    void deleteSupportResponse_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).delete(id);
        service.deleteSupportResponse(id);
        verify(repository, times(1)).delete(id);
    }
}
