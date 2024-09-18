package com.gra.recist.domain.service;

import com.gra.recist.domain.model.RecistAnalysis;
import com.gra.recist.domain.model.Segmentation;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RecistAnalysisService {
    }

    public RecistAnalysis analyseSegmentations(Collection<Segmentation> segmentations) {
        List<Segmentation> segmentationInTime = segmentations.stream().sorted(Comparator.comparing(Segmentation::getStudyDate)).toList();

    }
}
