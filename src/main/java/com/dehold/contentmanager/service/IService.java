package com.dehold.contentmanager.service;

import java.util.List;
import java.util.UUID;

public interface IService<T> {
    T create(T entity);

    T findById(UUID id);

    List<T> findAll();

    T update(UUID id, T entity);

    void delete(UUID id);
}
