package com.gra.recist.presentation.analyser;

import com.gra.recist.application.readmodel.RecistAnalysisReadModel;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class RecistAnalysisCellFactory implements Callback<ListView<RecistAnalysisReadModel>, ListCell<RecistAnalysisReadModel>> {
    @Override
    public ListCell<RecistAnalysisReadModel> call(ListView<RecistAnalysisReadModel> param) {
        return new ListCell<>() {
            @Override
            public void updateItem(RecistAnalysisReadModel recistAnalysis, boolean empty) {
                super.updateItem(recistAnalysis, empty);
                if (empty || recistAnalysis == null) {
                    setText(null);
                } else {
                    setText(recistAnalysis.getName());
                }
            }
        };
    }

}
