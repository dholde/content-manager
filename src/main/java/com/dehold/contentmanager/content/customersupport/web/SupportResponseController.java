package com.dehold.contentmanager.content.customersupport.web;

import com.dehold.contentmanager.content.customersupport.model.SupportResponse;
import com.dehold.contentmanager.content.customersupport.service.SupportResponseService;
import com.dehold.contentmanager.content.customersupport.web.dto.CreateSupportResponseRequest;
import com.dehold.contentmanager.content.customersupport.web.dto.UpdateSupportResponseRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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
        SupportResponse response = new SupportResponse(UUID.randomUUID(), UUID.randomUUID(), request.getText(), request.getSupportRequest(), Instant.now(), Instant.now());
        service.createSupportResponse(response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportResponse> get(@PathVariable UUID id) {
        SupportResponse supportResponse = service.getSupportResponse(id); // This will throw EntityNotFoundException if not found
        return ResponseEntity.ok(supportResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupportResponse> update(@PathVariable UUID id, @RequestBody UpdateSupportResponseRequest request) {
        SupportResponse supportResponse = service.getSupportResponse(id);
        SupportResponse updated = new SupportResponse(id, UUID.randomUUID(), request.getText(), request.getSupportRequest(), supportResponse.getCreatedAt(), Instant.now());
        service.updateSupportResponse(updated);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteSupportResponse(id);
        return ResponseEntity.noContent().build();
    }
}

