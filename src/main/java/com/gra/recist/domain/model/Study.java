package com.gra.recist.domain.model;

import com.gra.recist.domain.common.Entity;

public class Study extends Entity<Study.StudyId> {

    public Study(StudyId studyId) {
        super(studyId);
    }

    public record StudyId(String source, String studyId) {
    }
}