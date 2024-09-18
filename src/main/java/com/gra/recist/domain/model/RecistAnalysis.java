package com.gra.recist.domain.model;

import com.gra.recist.domain.event.NewRecistAnalysisCreated;
import com.gra.recist.domain.event.RecistSegmentationChanged;
import com.gra.recist.domain.repository.AggregateRoot;

import java.util.*;
import java.util.stream.Collectors;

public class RecistAnalysis extends AggregateRoot {

    private final Map<String, Segmentation> segmentations;
    private Set<Artefact> artefacts;
    private Integer result = null;

    public RecistAnalysis(List<Segmentation> segmentations, Set<Artefact> artefacts) {
        super(UUID.randomUUID());
        this.segmentations = segmentations.stream().collect(Collectors.toMap(Segmentation::getSegmentationId, x -> x));
        this.artefacts = artefacts;
        this.sendEvent(new NewRecistAnalysisCreated());
    }

    public void addArtefact(Artefact artefact) {
        artefacts.add(artefact);
    }

    public void changeSegmentationData(String segmentationId, SegmentationData segmentationData) {
        // TODO Validate against existing artefacts
        segmentations.get(segmentationId).modifySegmentationRegion(segmentationData);
        sendEvent(new RecistSegmentationChanged());
    }

    public void performAnalysis() {
        this.result = 1;
    }
}
