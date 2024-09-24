package com.gra.recist.application.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gra.recist.domain.model.RecistAnalysis;
import com.gra.recist.infrastructure.repository.entity.RecistAnalysisRepository;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Singleton
public class RecistAnalysisService {

    private final RecistAnalysisRepository recistAnalysisRepository;

    @Inject
    public RecistAnalysisService(RecistAnalysisRepository recistAnalysisRepository) {
        this.recistAnalysisRepository = recistAnalysisRepository;
    }

    public CompletableFuture<UUID> createRecistAnalysis(String analysisName) {
        RecistAnalysis recistAnalysis = new RecistAnalysis(analysisName);
        recistAnalysisRepository.save(recistAnalysis);

        return CompletableFuture.completedFuture(recistAnalysis.getId());
    }
}
