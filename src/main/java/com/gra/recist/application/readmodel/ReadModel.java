package com.gra.recist.application.readmodel;

import com.gra.recist.domain.event.IEvent;
import com.gra.recist.domain.model.RecistAnalysis;
import com.gra.recist.domain.repository.Entity;
import com.gra.recist.domain.repository.EntityIdentifier;

public interface ReadModel<T extends Entity> {
    void dispatchEvent(IEvent event);
}
