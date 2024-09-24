package com.gra.recist.infrastructure.storage;

import com.google.inject.Inject;
import com.gra.recist.application.readmodel.ReadModel;
import com.gra.recist.application.readmodel.RecistAnalysisReadModel;
import com.gra.recist.application.repository.EntityRepository;
import com.gra.recist.domain.repository.Entity;
import com.gra.recist.domain.repository.EntityIdentifier;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ReadModelCache {

    @Inject
    EntityRepository<Entity> entityRepository;

    private Map<EntityIdentifier, WeakReference<ReadModel>> readModels = new HashMap<>();

    public WeakReference<ReadModel> get(EntityIdentifier entityIdentifier) {
        Entity entity = entityRepository.get(entityIdentifier.getUuid());
        //create and map read model with entity
        readModels.put(entityIdentifier, new RecistAnalysisReadModel());
        return readModels.get(entityIdentifier);
    }
}
