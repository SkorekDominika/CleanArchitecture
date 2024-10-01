package com.gra.recist.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.gra.recist.domain.repository.DicomDataRepository;
import com.gra.recist.infrastructure.repository.entity.FileSystemDicomDataRepository;

public class PersistenceModule extends AbstractModule {

    @Provides
    @Singleton
    DicomDataRepository dicomDataRepository() {
        return new FileSystemDicomDataRepository();
    }

}
