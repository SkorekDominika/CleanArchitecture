package com.gra.recist.domain.model;

import java.util.*;
import java.util.stream.Collectors;

public class RecistAnalysis {

    private final Map<String, Segmentation> segmentations;
    private Set<Artefact> artefacts;
    private Integer result = null;

    public RecistAnalysis(List<Segmentation> segmentations, Set<Artefact> artefacts) {
        this.segmentations = segmentations.stream().collect(Collectors.toMap(Segmentation::getSegmentationId, x -> x));
        this.artefacts = artefacts;
    }

    public void addArtefact(Artefact artefact) {
        artefacts.add(artefact);
    }

    public void changeSegmentationData(String segmentationId, SegmentationData segmentationData) {
        // TODO Validate against existing artefacts
        segmentations.get(segmentationId).modifySegmentationRegion(segmentationData);
    }

    public void performAnalysis() {
        this.result = 1;
    }
}
