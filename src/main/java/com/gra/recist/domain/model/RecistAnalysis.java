package com.gra.recist.domain.model;

import com.gra.recist.domain.event.NewEntityCreated;
import com.gra.recist.domain.repository.AggregateRoot;

public class RecistAnalysis extends AggregateRoot {


    private final String name;

    public RecistAnalysis(String name) {
        this.name = name;

        this.sendEvent(new NewEntityCreated());
    }

}
