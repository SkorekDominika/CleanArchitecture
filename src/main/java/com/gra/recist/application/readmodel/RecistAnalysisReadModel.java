package com.gra.recist.application.readmodel;

import com.gra.recist.domain.event.EntityModified;
import com.gra.recist.domain.event.IEvent;
import com.gra.recist.domain.model.RecistAnalysis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.UUID;


public class RecistAnalysisReadModel implements ReadModel<RecistAnalysis> {

    private final UUID id;
    private ObservableList<ArtefactReadModel> artefacts = FXCollections.observableList(new ArrayList<>());


    public RecistAnalysisReadModel(RecistAnalysis recistAnalysis) {
        this.id = recistAnalysis.getId();
        updateState(recistAnalysis);
    }

    public ObservableList<ArtefactReadModel> getArtefacts() {
        return artefacts;
    }

    @Override
    public void dispatchEvent(IEvent event) {
        switch (event) {
            case EntityModified entityModified -> update(entityModified);
            default -> throw new UnsupportedOperationException("Unhandled event: %s exception".formatted(event));
        }
    }

    private void update(IEvent event) {
        if (event.getEntity() instanceof RecistAnalysis recistAnalysis) {
            updateState(recistAnalysis);
        } else {
            System.out.println("Entity is not a RecistAnalysis!");
        }
    }

    private void updateState(RecistAnalysis recistAnalysis) {
        artefacts.clear();
        artefacts.addAll(recistAnalysis.getArtefacts().stream().map(artefact ->
                new ArtefactReadModel(artefact.getId(), artefact.getLesionSize())).toList());
    }
}
