package com.gra.recist.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.gra.recist.application.service.StudyService;
import com.gra.recist.domain.repository.DicomDataRepository;
import com.gra.recist.infrastructure.di.scope.hp.HangingProtocolScoped;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class AppServicesModule extends AbstractModule {

    @Provides
    @Named("backendTP")
    ExecutorService backendExecutors() {
        return Executors.newCachedThreadPool();
    }

    @Provides
    @HangingProtocolScoped
    StudyService studyService(DicomDataRepository dicomDataRepository, @Named("backendTP") ExecutorService backendExecutors) {
        return new StudyService(dicomDataRepository, backendExecutors);
    }
}
