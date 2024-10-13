package com.clean.arch.application.common;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;

public class ObservableService<T> {

  private final Set<Consumer<T>> weakConsumers = Collections.newSetFromMap(new WeakHashMap<>());

  public void subscribe(Consumer<T> consumer) {
    weakConsumers.add(consumer);
  }

  protected void notify(T event) {
    weakConsumers.forEach(c -> c.accept(event));
  }
}
