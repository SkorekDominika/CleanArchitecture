package com.gra.recist.domain.event;

import com.gra.recist.domain.common.Entity;

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
