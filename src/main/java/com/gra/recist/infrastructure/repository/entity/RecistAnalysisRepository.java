package com.gra.recist.infrastructure.repository.entity;

import com.google.inject.Inject;
import com.gra.recist.application.repository.Repository;
import com.gra.recist.domain.model.RecistAnalysis;
import com.gra.recist.infrastructure.notification.MessageBroker;
import com.gra.recist.infrastructure.storage.EntityCache;

import java.util.UUID;

public class RecistAnalysisRepository implements Repository<RecistAnalysis> {

    @Inject
    EntityCache<RecistAnalysis> entityCache;

    @Inject
    MessageBroker messageBroker;

    public RecistAnalysisRepository() {
    }

    @Override
    public RecistAnalysis get(UUID id) {
        return entityCache.get(id);
    }

    public void save(RecistAnalysis recistAnalysis) {
        entityCache.save(recistAnalysis);
        recistAnalysis.drainEvents(messageBroker::broadcast);
    }
}