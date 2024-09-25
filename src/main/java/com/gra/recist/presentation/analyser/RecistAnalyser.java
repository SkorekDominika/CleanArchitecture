package com.gra.recist.presentation.analyser;

import com.google.inject.Inject;
import com.gra.recist.application.readmodel.RecistAnalysisReadModel;
import com.gra.recist.application.repository.ReadModelRepository;
import com.gra.recist.application.service.RecistAnalysisService;
import com.gra.recist.infrastructure.repository.readmodel.RecistAnalysisReadModelRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RecistAnalyser {
    @FXML
    private Label welcomeText;

    @FXML
    ListView<RecistAnalysisReadModel> recistAnalysisList;

    @Inject
    RecistAnalysisService recistAnalysisService;

    @Inject
    RecistAnalysisReadModelRepository analysisReadModelRepository;

    private int counter;

    @FXML
    protected void onAddNewRecistAnalysis() {
        counter++;
        String analysisName = "Recist Analysis " + counter;

        CompletableFuture<UUID> recistAnalysisFuture = recistAnalysisService.createRecistAnalysis(analysisName);

        recistAnalysisFuture
                .thenApply(id -> analysisReadModelRepository.get(id))
                .thenAccept(recistAnalysisReadModel -> recistAnalysisList.getItems().add(recistAnalysisReadModel));
    }
}