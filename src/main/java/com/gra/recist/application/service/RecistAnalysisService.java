package com.gra.recist.application.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gra.recist.domain.model.RecistAnalysis;
import com.gra.recist.domain.repository.EntityIdentifier;
import com.gra.recist.domain.repository.RecistAnalysisRepository;

import java.util.concurrent.CompletableFuture;

@Singleton
public class RecistAnalysisService {

    private final RecistAnalysisRepository recistAnalysisRepository;

    @Inject
    public RecistAnalysisService(RecistAnalysisRepository recistAnalysisRepository) {
        this.recistAnalysisRepository = recistAnalysisRepository;
    }

    public CompletableFuture<RecistAnalysis> createRecistAnalysis(String analysisName) {
        RecistAnalysis recistAnalysis = new RecistAnalysis(analysisName);
        EntityIdentifier entityIdentifier = EntityIdentifier.from(recistAnalysis);
        recistAnalysisRepository.save(recistAnalysis);

        return CompletableFuture.completedFuture(null);
    }
}
