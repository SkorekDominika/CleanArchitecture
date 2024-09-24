package com.gra.recist.domain.repository;

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
}
