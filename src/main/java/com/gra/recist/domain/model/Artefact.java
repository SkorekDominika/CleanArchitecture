package com.gra.recist.domain.model;

import com.gra.recist.domain.repository.Entity;

import java.util.UUID;

public class Artefact extends Entity {
    private Integer lesionSize;

    public Artefact(Integer lesionSize) {
        this.lesionSize = lesionSize;
    }

    public Artefact(UUID artefactId, Integer lesionSize) {
        super(artefactId);
        this.lesionSize = lesionSize;
    }

    public Integer getLesionSize() {
        return lesionSize;
    }



}
