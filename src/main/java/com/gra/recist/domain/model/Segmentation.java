package com.gra.recist.domain.model;


import java.util.Date;
import java.util.List;

public class Segmentation {
    private String segmentationId;
    private String studyId;
    private Date studyDate;
    private SegmentationData data;

    public void modifySegmentationRegion(SegmentationData data) {
        // TODO Check data dimensions on given study image before setting it.
        this.data = data;
    }

    public String getSegmentationId() {
        return segmentationId;
    }

    public String getStudyId() {
        return studyId;
    }

    public Date getStudyDate() {
        return studyDate;
    }

}
