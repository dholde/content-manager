package com.dehold.contentmanager.customersupport.web;

import com.dehold.contentmanager.customersupport.model.SupportResponse;
import com.dehold.contentmanager.customersupport.service.SupportResponseService;
import com.dehold.contentmanager.customersupport.web.dto.CreateSupportResponseRequest;
import com.dehold.contentmanager.customersupport.web.dto.UpdateSupportResponseRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/support-responses")
public class SupportResponseController {
    private final SupportResponseService service;

    public SupportResponseController(SupportResponseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SupportResponse> create(@RequestBody CreateSupportResponseRequest request) {
        SupportResponse response = new SupportResponse(UUID.randomUUID(), request.getText(), request.getSupportRequest(), Instant.now(), Instant.now());
        service.createSupportResponse(response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportResponse> get(@PathVariable UUID id) {
        Optional<SupportResponse> response = service.getSupportResponse(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportResponse> update(@PathVariable UUID id, @RequestBody UpdateSupportResponseRequest request) {
        Optional<SupportResponse> existing = service.getSupportResponse(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SupportResponse updated = new SupportResponse(id, request.getText(), request.getSupportRequest(), existing.get().getCreatedAt(), Instant.now());
        service.updateSupportResponse(updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteSupportResponse(id);
        return ResponseEntity.noContent().build();
    }
}

