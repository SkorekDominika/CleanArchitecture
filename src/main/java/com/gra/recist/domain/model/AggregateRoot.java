package com.gra.recist.domain.model;

import com.gra.recist.domain.event.IEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AggregateRoot {

    private UUID id;
    private List<IEvent> events = new ArrayList<>();

}
