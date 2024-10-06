package com.clean.arch.application.service;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.clean.arch.domain.model.DicomData;
import com.clean.arch.domain.model.valueobject.DicomDataSource;
import com.clean.arch.domain.model.valueobject.FrameId;
import com.clean.arch.domain.repository.DicomDataRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


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

    public CompletableFuture<DicomData> loadStudy(FrameId frameId) {
        return cache.get(frameId);
    }

    public List<CompletableFuture<DicomData>> loadStudy(List<FrameId> frameIds) {
        return frameIds.stream().map(cache::get).toList();
    }

    public List<FrameId> loadAllHeaders(DicomDataSource dataSource) {
        return dicomDataRepository.getAllIds(dataSource);
    }
}
