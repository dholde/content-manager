package com.dehold.contentmanager.validation.web;

import com.dehold.contentmanager.validation.model.ForbiddenWords;
import com.dehold.contentmanager.validation.service.ForbiddenWordsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/forbidden-words")
@Validated
public class ForbiddenWordsController {

    private final ForbiddenWordsService forbiddenWordsService;

    public ForbiddenWordsController(ForbiddenWordsService forbiddenWordsService) {
        this.forbiddenWordsService = forbiddenWordsService;
    }

    @PostMapping
    public ResponseEntity<ForbiddenWords> createForbiddenWords(@RequestBody ForbiddenWords request) {
        var forbiddenWords = forbiddenWordsService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(forbiddenWords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForbiddenWords> getForbiddenWords(@PathVariable UUID id) {
        var forbiddenWords = forbiddenWordsService.findById(id);
        return ResponseEntity.ok(forbiddenWords);
    }

    @GetMapping
    public ResponseEntity<List<ForbiddenWords>> getAllForbiddenWords() {
        List<ForbiddenWords> forbiddenWordsList = forbiddenWordsService.findAll();
        return ResponseEntity.ok(forbiddenWordsList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForbiddenWords> updateForbiddenWords(@PathVariable UUID id, @RequestBody ForbiddenWords request) {
        var forbiddenWords = forbiddenWordsService.update(id, request);
        return ResponseEntity.ok(forbiddenWords);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForbiddenWords(@PathVariable UUID id) {
        forbiddenWordsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
