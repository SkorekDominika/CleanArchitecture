package com.clean.arch.domain.event;

import com.clean.arch.domain.common.Entity;

public class NewEntityCreated<T extends Entity> implements IEvent {
    private final T entity;

    public NewEntityCreated(T entity) {
        this.entity = entity;
    }

    @Override
    public T getEntity() {
        return entity;
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
