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
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class MessageBroker {

    Map<EntityIdentifier, List<WeakReference<ReadModel<? extends Entity>>>> readModels = new ConcurrentHashMap<>();
    ReferenceQueue<ReadModel<? extends Entity>> referenceQueue = new ReferenceQueue<>();
    Map<Reference<ReadModel<? extends Entity>>, Runnable> referenceCleaners = new ConcurrentHashMap<>();


    public MessageBroker() {
        Thread cleanerThread = createCleanerThread();
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    private Thread createCleanerThread() {
        return new Thread(() -> {
            while (true) {
                try {
                    cleanUpReferences();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void cleanUpReferences() throws InterruptedException {
        Reference<? extends ReadModel<? extends Entity>> toClean = referenceQueue.remove();
        Optional.ofNullable(referenceCleaners.remove(toClean)).ifPresent(Runnable::run);
    }

    public void broadcast(IEvent event) {
        EntityIdentifier entityIdentifier = createEntityIdentifier(event);
        Optional.ofNullable(readModels.get(entityIdentifier))
                .ifPresent(weakReferences -> weakReferences.forEach(reference ->
                        Optional.ofNullable(reference.get())
                                .ifPresent(readModel -> readModel.dispatchEvent(event))));
    }

    private static EntityIdentifier createEntityIdentifier(IEvent event) {
        Entity entity = event.getEntity();
        UUID id = event.isGlobal() ? null : entity.getId();
        return new EntityIdentifier(entity.getClass(), id);
    }

    public void subscribe(EntityIdentifier entityIdentifier, ReadModel<? extends Entity> readModel) {
        WeakReference<ReadModel<? extends Entity>> weakReference = new WeakReference<>(readModel, referenceQueue);
        readModels.computeIfAbsent(entityIdentifier, id -> new CopyOnWriteArrayList<>()).add(weakReference);
        referenceCleaners.put(weakReference, () -> cleanUpReadModels(entityIdentifier, weakReference));
    }

    private void cleanUpReadModels(EntityIdentifier entityIdentifier, WeakReference<ReadModel<? extends Entity>> weakReference) {
        readModels.computeIfAbsent(entityIdentifier, id -> new CopyOnWriteArrayList<>()).remove(weakReference);
        readModels.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }
}