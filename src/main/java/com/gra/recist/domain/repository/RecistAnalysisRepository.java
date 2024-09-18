package com.gra.recist.domain.repository;

import com.gra.recist.domain.model.RecistAnalysis;

import java.util.UUID;

public class RecistAnalysisRepository {

    private final CrudRepository<UUID, RecistAnalysis> delegate;
    private final IEventRepository eventDelegate;

    public RecistAnalysisRepository(CrudRepository<UUID, RecistAnalysis> delegate, IEventRepository eventDelegate) {
        this.delegate = delegate;
        this.eventDelegate = eventDelegate;
    }

    public RecistAnalysis get(UUID id) {
        return delegate.read(id);
    }
    public RecistAnalysis save(RecistAnalysis recistAnalysis){
        RecistAnalysis res = delegate.update(recistAnalysis);
        ((AggregateRoot)recistAnalysis).drainEvents(eventDelegate::persist);
        return res;
    }

}
