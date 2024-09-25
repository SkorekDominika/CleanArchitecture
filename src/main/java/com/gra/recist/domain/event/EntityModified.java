package com.gra.recist.domain.event;

import com.gra.recist.domain.repository.Entity;

public class EntityModified implements IEvent {

    private final Entity entity;

    public EntityModified(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
