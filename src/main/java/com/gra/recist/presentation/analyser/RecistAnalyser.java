package com.gra.recist.presentation.analyser;

import com.google.inject.Inject;
import com.gra.recist.application.readmodel.ArtefactReadModel;
import com.gra.recist.application.readmodel.RecistAnalysisReadModel;
import com.gra.recist.application.service.RecistAnalysisService;
import com.gra.recist.infrastructure.repository.readmodel.RecistAnalysisReadModelRepository;
import com.gra.recist.presentation.util.MainFxLoader;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.IOException;
import java.util.UUID;

public class RecistAnalyser {

    @FXML
    Label result;

    @FXML
    ListView<ArtefactReadModel> recistAnalysisList;

    @Inject
    RecistAnalysisService recistAnalysisService;

    @Inject
    RecistAnalysisReadModelRepository analysisReadModelRepository;

    private RecistAnalysisReadModel readModel;
    private UUID recistId;

    @Inject
    private MainFxLoader mainFxLoader;

    @FXML
    public void initialize() {
        recistAnalysisList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ArtefactReadModel> call(ListView<ArtefactReadModel> param) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(ArtefactReadModel data, boolean empty) {
                        super.updateItem(data, empty);
                        if (empty || data == null) {
                            setGraphic(null);
                        } else {
                            try {
                                MainFxLoader.ViewControllerReference<Parent, ArtefactView> node = mainFxLoader.load(ArtefactView.class);
                                ArtefactView controller = node.controller();
                                controller.artefactLabel.setText("Artefact " + data.id());
                                controller.lesionSizeSpinner.getValueFactory().setValue(data.lesionSize());
                                controller.lesionSizeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> recistAnalysisService.recalculateAnalysis(recistId, data.id(), newValue));
                                setGraphic(node.viewNode());
                            } catch (IOException e) {
                                System.out.println("DUPA" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }

                };
            }
        });
    }

    public void setRecistAnalysis(UUID id) {
        this.recistId = id;
        readModel = analysisReadModelRepository.get(recistId);
        readModel.getArtefacts().addListener((ListChangeListener<ArtefactReadModel>) c -> {
            try {
                while (c.next()) {
                    c.getRemoved().stream().map(ArtefactReadModel::id).forEach(idToRemove -> recistAnalysisList.getItems().removeIf(x -> x.id().equals(idToRemove)));
                    c.getAddedSubList().forEach(added -> recistAnalysisList.getItems().add(added));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    protected void onAddNewRecistAnalysis() {
        recistAnalysisService.createArtefact(this.recistId);
    }

}