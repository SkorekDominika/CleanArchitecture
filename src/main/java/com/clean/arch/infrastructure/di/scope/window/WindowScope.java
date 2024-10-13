package com.clean.arch.infrastructure.di.scope.window;

import com.clean.arch.infrastructure.di.scope.hp.HangingProtocolScoped;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import javafx.stage.Window;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Handles creation of objects annotated with {@link WindowScope} providing the same instances within window
 */
public class WindowScope implements Scope {

    private final ThreadLocal<Window> currentThreadStage = new ThreadLocal<>();

    private final Map<Window, Map<Key<?>, Object>> stageContext = new WeakHashMap<>();

    @Override
    public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
        return () -> {
            Window stage = currentThreadStage.get();
            if (stage == null) {
                throw new RuntimeException("No stage connected to current thread");
            } else {
                Map<Key<?>, Object> stageScopedObjects = stageContext.get(stage);
                if (stageScopedObjects == null) {
                    throw new RuntimeException("Stage cache not initialized.");
                }
                synchronized (stageScopedObjects) {
                    T object = (T) stageScopedObjects.get(key);
                    if (object == null) {
                        object = unscoped.get();
                        stageScopedObjects.put(key, object);
                    }
                    return object;
                }
            }
        };
    }

    public synchronized void enter(Window stage) {
        currentThreadStage.set(stage);
        stageContext.computeIfAbsent(stage, (key) -> new HashMap<>());
    }

    public void exit() {
        currentThreadStage.remove();
    }
}
