package com.clean.arch.domain.common;

import java.util.Objects;

public class EntityIdentifier {
  private final Class<? extends Entity> clazz;

  private final Object uuid;

  public EntityIdentifier(Class<? extends Entity> clazz, Object uuid) {
    this.clazz = clazz;
    this.uuid = uuid;
  }

  public Class<? extends Entity> getClazz() {
    return clazz;
  }

  public Object getUuid() {
    return uuid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EntityIdentifier that = (EntityIdentifier) o;
    return Objects.equals(clazz, that.clazz) && Objects.equals(uuid, that.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clazz, uuid);
  }
}
