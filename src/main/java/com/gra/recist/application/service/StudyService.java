package com.gra.recist.application.service;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import com.gra.recist.domain.model.DicomData;
import com.gra.recist.domain.model.valueobject.DicomDataSource;
import com.gra.recist.domain.model.valueobject.FrameId;
import com.gra.recist.domain.repository.DicomDataRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;


public class StudyService {


    private final DicomDataRepository dicomDataRepository;
    private final ExecutorService backendExecutors;
    private final AsyncLoadingCache<FrameId, DicomData> cache;

    public StudyService(DicomDataRepository dicomDataRepository, ExecutorService backendExecutors) {
        this.dicomDataRepository = dicomDataRepository;
        this.backendExecutors = backendExecutors;
        this.cache = Caffeine.newBuilder()
                .executor(backendExecutors)
                .buildAsync(dicomDataRepository::read);
    }

    public List<CompletableFuture<DicomData>> loadStudy(DicomDataSource dataSource) {
        List<FrameId> frameIds = dicomDataRepository.getAllIds(dataSource);
        return frameIds.stream().map(cache::get).collect(Collectors.toList());
    }
}
