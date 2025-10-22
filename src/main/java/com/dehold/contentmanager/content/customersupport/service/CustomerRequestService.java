package com.dehold.contentmanager.content.customersupport.service;

import com.dehold.contentmanager.content.customersupport.model.CustomerRequest;
import com.dehold.contentmanager.content.customersupport.repository.CustomerRequestRepository;
import com.dehold.contentmanager.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerRequestService {
    private final CustomerRequestRepository repository;

    public CustomerRequestService(CustomerRequestRepository repository) {
        this.repository = repository;
    }

    public List<CustomerRequest> findAll() {
        return repository.findAll();
    }

    public CustomerRequest findById(UUID id) {
        return repository.getById(id)
                .orElseThrow(() -> EntityNotFoundException.of("CustomerRequest", id.toString()));
    }

    // Keep the optional version for internal use if needed
    public Optional<CustomerRequest> findByIdOptional(UUID id) {
        return repository.getById(id);
    }

    public void createCustomerRequest(CustomerRequest customerRequest) {
        repository.create(customerRequest);
    }

    public void updateCustomerRequest(CustomerRequest customerRequest) {
        repository.update(customerRequest);
    }

    public void save(CustomerRequest customerRequest) {
        repository.create(customerRequest);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
