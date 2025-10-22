package com.dehold.contentmanager.customersupport.service;

import com.dehold.contentmanager.customersupport.model.SupportResponse;
import com.dehold.contentmanager.customersupport.repository.SupportResponseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SupportResponseServiceImpl implements SupportResponseService {
    private final SupportResponseRepository repository;

    public SupportResponseServiceImpl(SupportResponseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createSupportResponse(SupportResponse response) {
        repository.create(response);
    }

    @Override
    public Optional<SupportResponse> getSupportResponse(UUID id) {
        return repository.getById(id);
    }

    @Override
    public void updateSupportResponse(SupportResponse response) {
        repository.update(response);
    }

    @Override
    public void deleteSupportResponse(UUID id) {
        repository.delete(id);
    }
}

