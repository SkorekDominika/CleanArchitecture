package com.clean.arch.application.readmodel;

import com.clean.arch.domain.common.Entity;
import com.clean.arch.domain.event.IEvent;

public interface ReadModel<T extends Entity> {
    void dispatchEvent(IEvent event);
}
