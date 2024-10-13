package com.clean.arch.domain.common;

import java.io.Serializable;
import java.util.Objects;

public class Entity<ID> implements Serializable {
  private final ID id;

  public Entity(ID id) {
    this.id = id;
  }

  public ID getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Entity<?> entity = (Entity<?>) o;
    return Objects.equals(id, entity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
