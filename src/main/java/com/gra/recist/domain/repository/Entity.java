package com.gra.recist.domain.repository;

import java.util.UUID;

public class Entity {
    private final UUID id;

    public Entity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}