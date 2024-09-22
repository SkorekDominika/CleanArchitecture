package com.gra.recist.presentation.analyser;

import com.google.inject.Inject;
import com.gra.recist.application.readmodel.RecistAnalysisReadModel;
import com.gra.recist.application.service.RecistAnalysisService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.concurrent.CompletableFuture;

public class RecistAnalyser {
    @FXML
    private Label welcomeText;

    @FXML
    ListView<RecistAnalysisReadModel> recistAnalysisList;

    @Inject
    RecistAnalysisService recistAnalysisService;

    private int counter;

    @FXML
    protected void onAddNewRecistAnalysis() {
        counter++;
        String analysisName = "Recist Analysis " + counter;

        CompletableFuture<RecistAnalysis> recistAnalysisFuture = recistAnalysisService.createRecistAnalysis(analysisName);

        recistAnalysisFuture
                .thenAccept(id -> recistRepository.get(id))
                .thenAccept(recistAnalysisReadModel -> recistAnalysisList.getItems().add(recistAnalysisReadModel));

    }
}