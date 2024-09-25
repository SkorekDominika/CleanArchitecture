package com.gra.recist.infrastructure.repository.readmodel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gra.recist.application.readmodel.RecistAnalysisReadModel;
import com.gra.recist.application.repository.ReadModelRepository;
import com.gra.recist.domain.model.RecistAnalysis;
import com.gra.recist.domain.repository.EntityIdentifier;
import com.gra.recist.infrastructure.notification.MessageBroker;
import com.gra.recist.infrastructure.repository.entity.RecistAnalysisRepository;

import java.util.UUID;

@Singleton
public class RecistAnalysisReadModelRepository implements ReadModelRepository<RecistAnalysisReadModel> {

    @Inject
    MessageBroker messageBroker;

    @Inject
    RecistAnalysisRepository recistAnalysisRepository;

    public RecistAnalysisReadModelRepository() {
        initializeMessageHandler();
    }

    @Override
    public RecistAnalysisReadModel get(UUID id) {
        EntityIdentifier entityIdentifier = new EntityIdentifier(RecistAnalysis.class, id);
        RecistAnalysis recistAnalysis = recistAnalysisRepository.get(id);
        RecistAnalysisReadModel readModel = new RecistAnalysisReadModel(recistAnalysis);
        messageBroker.subscribe(entityIdentifier, readModel);
        return readModel;
    }

    private void initializeMessageHandler() {

    }
}
