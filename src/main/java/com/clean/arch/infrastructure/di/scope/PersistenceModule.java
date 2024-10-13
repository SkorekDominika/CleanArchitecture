package com.clean.arch.infrastructure.di.scope;

import com.clean.arch.domain.repository.DicomDataRepository;
import com.clean.arch.infrastructure.repository.FileSystemDicomDataRepository;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class PersistenceModule extends AbstractModule {

    @Provides
    @Singleton
    DicomDataRepository dicomDataRepository() {
        return new FileSystemDicomDataRepository();
    }

}
