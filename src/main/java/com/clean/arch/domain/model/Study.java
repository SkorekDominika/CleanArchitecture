package com.clean.arch.domain.model;

import com.clean.arch.domain.common.Entity;

public class Study extends Entity<Study.StudyId> {

    public Study(StudyId studyId) {
        super(studyId);
    }

    public record StudyId(String source, String studyId) {
    }
}