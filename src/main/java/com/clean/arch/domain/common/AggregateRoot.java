package com.clean.arch.domain.common;

import com.clean.arch.domain.event.IEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AggregateRoot<ID> extends Entity<ID> {
  private transient List<IEvent> events;

  public AggregateRoot(ID id) {
    super(id);
    this.events = new ArrayList<>();
  }

  protected void sendEvent(IEvent event) {
    this.events.add(event);
  }

  public void drainEvents(Consumer<IEvent> consumer) {
    List<IEvent> oldEvents = events;
    events = new ArrayList<>();
    oldEvents.forEach(consumer);
  }
}
