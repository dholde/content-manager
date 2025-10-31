package com.dehold.contentmanager.validation.service;

import com.dehold.contentmanager.exception.EntityNotFoundException;
import com.dehold.contentmanager.service.IService;
import com.dehold.contentmanager.validation.model.ForbiddenWords;
import com.dehold.contentmanager.validation.repository.ForbiddenWordsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ForbiddenWordsService implements IService<ForbiddenWords> {
    private final ForbiddenWordsRepository repository;

    public ForbiddenWordsService(ForbiddenWordsRepository repository) {
        this.repository = repository;
    }

    @Override
    public ForbiddenWords create(ForbiddenWords entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public ForbiddenWords findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.of("ForbiddenWords", id.toString()));
    }

    @Override
    public List<ForbiddenWords> findAll() {
        return repository.findAll();
    }

    @Override
    public ForbiddenWords update(UUID id, ForbiddenWords entity) {
        findById(id);
        repository.save(entity);
        return entity;
    }

    @Override
    public void delete(UUID id) {
        repository.findById(id).ifPresent(repository::delete);
    }
}
