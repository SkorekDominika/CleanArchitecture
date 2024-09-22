package com.gra.recist.application.repository;

import com.gra.recist.application.readmodel.ReadModel;

import java.util.UUID;

public interface ReadModelRepository<T extends ReadModel> {


    T get(UUID id);
}
