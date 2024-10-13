package com.clean.arch.application.repository;

import com.clean.arch.application.readmodel.ReadModel;

import java.util.UUID;

public interface ReadModelRepository<T extends ReadModel> {
  T get(UUID id);
}
