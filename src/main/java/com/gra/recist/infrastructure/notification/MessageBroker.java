package com.gra.recist.infrastructure.notification;

import com.google.inject.Singleton;
import com.gra.recist.application.readmodel.ReadModel;
import com.gra.recist.domain.event.IEvent;
import com.gra.recist.domain.repository.Entity;
import com.gra.recist.domain.repository.EntityIdentifier;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class MessageBroker {

    private final Thread cleanerThread;
    Map<EntityIdentifier, List<WeakReference<ReadModel<? extends Entity>>>> readModels = new ConcurrentHashMap<>();

    ReferenceQueue<ReadModel<? extends Entity>> referenceQueue = new ReferenceQueue<>();

    Map<Reference<ReadModel<? extends Entity>>, Runnable> referenceCleaners = new ConcurrentHashMap<>();


    public MessageBroker() {
        this.cleanerThread = new Thread(
                () -> {
                    while (true) {
                        Reference<? extends ReadModel<? extends Entity>> toClean = referenceQueue.poll();
                        Optional.ofNullable(referenceCleaners.remove(toClean)).ifPresent(Runnable::run);
                    }
                }
        );
        this.cleanerThread.setDaemon(true);
        this.cleanerThread.start();
    }

    public void broadcast(IEvent<? extends Entity> event) {
        Entity entity = event.getEntity();
        EntityIdentifier entityIdentifier = new EntityIdentifier(entity.getClass(), entity.getId());
        Optional.ofNullable(readModels.get(entityIdentifier))
                .ifPresent(weakReferences -> weakReferences.forEach(reference -> reference.get().dispatchEvent(event)));
    }

    public void subscribe(EntityIdentifier entityIdentifier, ReadModel<? extends Entity> readModel) {
        WeakReference<ReadModel<? extends Entity>> weakReference = new WeakReference<>(readModel, referenceQueue);
        readModels.computeIfAbsent(entityIdentifier, id -> new CopyOnWriteArrayList<>()).add(weakReference);
        referenceCleaners.put(weakReference, () -> {
            readModels.computeIfAbsent(entityIdentifier, id -> new CopyOnWriteArrayList<>()).remove(weakReference);
            readModels.entrySet().removeIf(entry -> entry.getValue().isEmpty());
        });
    }
}
