package com.gra.recist.domain.event;

import com.gra.recist.domain.repository.Entity;

public interface IEvent<T extends Entity> {
    default public String getName() {
        return getClass().getName();
    }

    T getEntity();
}
