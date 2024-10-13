package com.clean.arch.infrastructure.di.scope;

import com.clean.arch.application.service.StudyService;
import com.clean.arch.domain.repository.DicomDataRepository;
import com.clean.arch.infrastructure.di.scope.hp.HangingProtocolScoped;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServicesModule extends AbstractModule {

  @Provides
  @Named("backendTP")
  ExecutorService backendExecutors() {
    return Executors.newCachedThreadPool();
  }

  @Provides
  @HangingProtocolScoped
  StudyService studyService(
      DicomDataRepository dicomDataRepository,
      @Named("backendTP") ExecutorService backendExecutors) {
    return new StudyService(dicomDataRepository, backendExecutors);
  }
}
