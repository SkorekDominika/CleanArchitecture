package com.gra.recist.domain.repository;

import java.util.Objects;
import java.util.UUID;

public class EntityIdentifier{
    private final Class<? extends Entity> clazz;

    private final UUID uuid;
    public EntityIdentifier(Class<? extends Entity> clazz, UUID uuid) {
        this.clazz = clazz;
        this.uuid = uuid;
    }

    public Class<? extends Entity> getClazz() {
        return clazz;
    }

    public UUID getUuid() {
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
