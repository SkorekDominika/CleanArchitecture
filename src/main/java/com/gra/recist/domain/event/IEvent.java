package com.gra.recist.domain.event;

import com.gra.recist.domain.repository.Entity;

public interface IEvent {
    default public String getName() {
        return getClass().getName();
    }

    Entity getEntity();

    boolean isGlobal();
}
