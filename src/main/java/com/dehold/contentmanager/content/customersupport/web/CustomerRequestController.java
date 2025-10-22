package com.dehold.contentmanager.content.customersupport.web;

import com.dehold.contentmanager.content.customersupport.model.CustomerRequest;
import com.dehold.contentmanager.content.customersupport.service.CustomerRequestService;
import com.dehold.contentmanager.content.customersupport.web.dto.CustomerRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer-requests")
public class CustomerRequestController {
    private final CustomerRequestService service;

    public CustomerRequestController(CustomerRequestService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerRequestDto> getAll() {
        return service.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerRequestDto> getById(@PathVariable UUID id) {
        Optional<CustomerRequest> request = service.findById(id);
        return request.map(r -> ResponseEntity.ok(toDto(r)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerRequestDto> create(@RequestBody CustomerRequestDto dto) {
        CustomerRequest entity = new CustomerRequest(
                UUID.randomUUID(),
                dto.getText(),
                dto.getSupportResponse(),
                dto.getCustomerId(),
                Instant.now(),
                Instant.now()
        );
        service.save(entity);
        return new ResponseEntity<>(toDto(entity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerRequestDto> update(@PathVariable UUID id, @RequestBody CustomerRequestDto dto) {
        Optional<CustomerRequest> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CustomerRequest entity = new CustomerRequest(
                id,
                dto.getText(),
                dto.getSupportResponse(),
                dto.getCustomerId(),
                existing.get().getCreatedAt(),
                Instant.now()
        );
        service.save(entity);
        return ResponseEntity.ok(toDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CustomerRequestDto toDto(CustomerRequest entity) {
        CustomerRequestDto dto = new CustomerRequestDto();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setSupportResponse(entity.getSupportResponse());
        dto.setCustomerId(entity.getCustomerId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
