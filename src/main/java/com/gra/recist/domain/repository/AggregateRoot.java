package com.gra.recist.domain.repository;

import com.gra.recist.domain.event.IEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class AggregateRoot implements Entity<UUID> {

    private final UUID id;
    private List<IEvent> events;

    public AggregateRoot(UUID id) {
        this.id = id;
        this.events = new ArrayList<>();
    }

    @Override
    public UUID getId() {
        return id;
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
