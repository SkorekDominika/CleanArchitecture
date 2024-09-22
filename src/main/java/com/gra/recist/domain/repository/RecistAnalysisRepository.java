package com.gra.recist.domain.repository;

import com.gra.recist.domain.model.RecistAnalysis;

import java.util.UUID;

public class RecistAnalysisRepository {

    private final IEventRepository eventDelegate;

    public RecistAnalysisRepository(IEventRepository eventDelegate) {
        this.eventDelegate = eventDelegate;
    }

    public RecistAnalysis save(RecistAnalysis recistAnalysis){
        delegate.save(recistAnalysis);
        ((AggregateRoot)recistAnalysis).drainEvents(eventDelegate::persist);
        return res;
    }

}
