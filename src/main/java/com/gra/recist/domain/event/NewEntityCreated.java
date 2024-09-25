package com.gra.recist.domain.event;

import com.gra.recist.domain.repository.Entity;

public class NewEntityCreated<T extends Entity> implements IEvent<T> {
    private final T entity;

    public NewEntityCreated(T entity) {
        this.entity = entity;
    }

    @Override
    public T getEntity() {
        return entity;
    }
}
