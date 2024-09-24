package com.gra.recist.infrastructure.storage;

import com.gra.recist.application.repository.EntityRepository;
import com.gra.recist.domain.repository.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityCache<T extends Entity> implements EntityRepository<T> {

    Map<UUID, T> entities = new HashMap<>();

    @Override
    public T get(UUID id) {
        return entities.get(id);
    }

    @Override
    public void save(T entity) {

        // should be copy
        entities.put(entity.getId(), entity);
    }
}