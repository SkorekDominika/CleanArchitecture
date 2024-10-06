package com.clean.arch.application.readmodel;

import com.clean.arch.domain.event.IEvent;
import com.clean.arch.domain.common.Entity;

public interface ReadModel<T extends Entity> {
    void dispatchEvent(IEvent event);
}
