package com.gra.recist.application.readmodel;

import com.gra.recist.domain.event.IEvent;
import com.gra.recist.domain.common.Entity;

public interface ReadModel<T extends Entity> {
    void dispatchEvent(IEvent event);
}
