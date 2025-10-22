package com.dehold.contentmanager.content.customersupport.service;

import com.dehold.contentmanager.content.customersupport.model.CustomerRequest;
import com.dehold.contentmanager.content.customersupport.repository.CustomerRequestRepository;
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

    public Optional<CustomerRequest> findById(UUID id) {
        return repository.findById(id);
    }

    public void save(CustomerRequest customerRequest) {
        repository.save(customerRequest);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
