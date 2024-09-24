package com.gra.recist.infrastructure.repository.readmodel;

import com.google.inject.Inject;
import com.gra.recist.application.readmodel.ReadModel;
import com.gra.recist.application.readmodel.RecistAnalysisReadModel;
import com.gra.recist.application.repository.ReadModelRepository;
import com.gra.recist.domain.model.RecistAnalysis;
import com.gra.recist.domain.repository.EntityIdentifier;
import com.gra.recist.infrastructure.notification.MessageBroker;
import com.gra.recist.infrastructure.storage.ReadModelCache;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class RecistAnalysisReadModelRepository implements ReadModelRepository<RecistAnalysisReadModel> {

    @Inject
    MessageBroker messageBroker;

    @Inject
    ReadModelCache readModelCache;

    public RecistAnalysisReadModelRepository(MessageBroker messageBroker, ReadModelCache readModelCache) {
        initializeMessageHandler();
    }

    @Override
    public RecistAnalysisReadModel get(UUID id) {
        EntityIdentifier entityIdentifier = new EntityIdentifier(RecistAnalysis.class, id);
        WeakReference<ReadModel> readModelWeakReference = readModelCache.get(entityIdentifier);
        messageBroker.subscribe(entityIdentifier, readModel);
        return null;
    }

    private void initializeMessageHandler() {

    }
}
