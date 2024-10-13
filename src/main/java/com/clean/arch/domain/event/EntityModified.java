package com.clean.arch.domain.event;

import com.clean.arch.domain.common.Entity;

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
