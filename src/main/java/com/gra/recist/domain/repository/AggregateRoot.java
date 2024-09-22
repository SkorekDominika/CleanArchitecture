package com.gra.recist.domain.repository;

import com.gra.recist.domain.event.IEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AggregateRoot extends Entity {
    private List<IEvent> events;

    public AggregateRoot() {
        this.events = new ArrayList<>();
    }

    protected void sendEvent(IEvent event) {
        this.events.add(event);
    }

    void drainEvents(Consumer<IEvent> consumer) {
        List<IEvent> oldEvents = events;
        events = new ArrayList<>();
        oldEvents.forEach(consumer);
    }
}
