package com.dehold.contentmanager.content.customersupport.web;

import com.dehold.contentmanager.content.customersupport.model.CustomerRequest;
import com.dehold.contentmanager.content.customersupport.service.CustomerRequestService;
import com.dehold.contentmanager.content.customersupport.web.dto.CustomerRequestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public CustomerRequestDto getById(@PathVariable UUID id) {
        return service.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("CustomerRequest not found"));
    }

    @PostMapping
    public CustomerRequestDto create(@RequestBody CustomerRequestDto dto) {
        CustomerRequest entity = toEntity(dto);
        service.save(entity);
        return toDto(entity);
    }

    @PutMapping("/{id}")
    public CustomerRequestDto update(@PathVariable UUID id, @RequestBody CustomerRequestDto dto) {
        CustomerRequest entity = toEntity(dto);
        entity.setId(id);
        service.save(entity);
        return toDto(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.deleteById(id);
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

    private CustomerRequest toEntity(CustomerRequestDto dto) {
        return new CustomerRequest(
                dto.getId(),
                dto.getText(),
                dto.getSupportResponse(),
                dto.getCustomerId(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
