package com.clean.arch.infrastructure.di.scope.hp;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

import java.util.HashMap;
import java.util.Map;

public class HangingProtocolScope implements Scope {

  private final ThreadLocal<Map<Key<?>, Object>> scopedObjects = new ThreadLocal<>();

  @SuppressWarnings("unchecked")
  @Override
  public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
    return () -> {
      Map<Key<?>, Object> scopedMap = scopedObjects.get();
      if (scopedMap == null) {
        throw new IllegalStateException("HangingProtocolScope not active");
      }

      T instance = (T) scopedMap.get(key);
      if (instance == null) {
        instance = unscoped.get();
        scopedMap.put(key, instance);
      }
      return instance;
    };
  }

  public void enter() {
    scopedObjects.set(new HashMap<>());
  }

  public void exit() {
    scopedObjects.remove();
  }
}
