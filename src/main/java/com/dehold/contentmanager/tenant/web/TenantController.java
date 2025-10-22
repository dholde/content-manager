package com.dehold.contentmanager.tenant.web;

import com.dehold.contentmanager.tenant.service.TenantService;
import com.dehold.contentmanager.tenant.web.dto.CreateTenantRequest;
import com.dehold.contentmanager.tenant.web.dto.TenantResponse;
import com.dehold.contentmanager.tenant.web.dto.UpdateTenantRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
@Validated
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<TenantResponse> createTenant(@RequestBody CreateTenantRequest request) {
        var tenant = tenantService.createTenant(request);
        var response = new TenantResponse(
                tenant.getId(),
                tenant.getAlias(),
                tenant.getEmail(),
                tenant.getCreatedAt(),
                tenant.getUpdatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantResponse> getTenant(@PathVariable UUID id) {
        var tenant = tenantService.getTenant(id);
        var response = new TenantResponse(
                tenant.getId(),
                tenant.getAlias(),
                tenant.getEmail(),
                tenant.getCreatedAt(),
                tenant.getUpdatedAt()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantResponse> updateTenant(@PathVariable UUID id, @RequestBody UpdateTenantRequest request) {
        var tenant = tenantService.updateTenant(id, request);
        var response = new TenantResponse(
                tenant.getId(),
                tenant.getAlias(),
                tenant.getEmail(),
                tenant.getCreatedAt(),
                tenant.getUpdatedAt()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable UUID id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }
}
