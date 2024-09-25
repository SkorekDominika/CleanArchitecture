package com.gra.recist.domain.model;

import com.gra.recist.domain.event.NewEntityCreated;
import com.gra.recist.domain.repository.AggregateRoot;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;


public class RecistAnalysis extends AggregateRoot implements Serializable {

    private final String name;

    public RecistAnalysis(String name) {
        this.name = name;
        // TODO
        NewEntityCreated event = new NewEntityCreated(SerializationUtils.clone(this));
        this.sendEvent(event);
    }

    public String getName() {
        return name;
    }
}
