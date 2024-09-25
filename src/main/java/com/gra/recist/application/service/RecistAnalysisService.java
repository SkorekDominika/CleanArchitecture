package com.gra.recist.application.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gra.recist.domain.model.Artefact;
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

    public CompletableFuture<UUID> createRecistAnalysis() {
        RecistAnalysis recistAnalysis = new RecistAnalysis();
        recistAnalysisRepository.save(recistAnalysis);
        return CompletableFuture.completedFuture(recistAnalysis.getId());
    }

    public CompletableFuture<Void> createArtefact(UUID analysisId) {
        RecistAnalysis recistAnalysis = recistAnalysisRepository.get(analysisId);
        recistAnalysis.addArtefact();
        recistAnalysisRepository.save(recistAnalysis);
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<Void> recalculateAnalysis(UUID recistId, UUID artefactId, Integer lesionSize) {
        RecistAnalysis recistAnalysis = recistAnalysisRepository.get(recistId);
        recistAnalysis.updateArtefact(artefactId, lesionSize);
        recistAnalysisRepository.save(recistAnalysis);
        return CompletableFuture.completedFuture(null);
    }
}
