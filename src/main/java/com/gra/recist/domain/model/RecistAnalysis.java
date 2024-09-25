package com.gra.recist.domain.model;

import com.gra.recist.domain.event.EntityModified;
import com.gra.recist.domain.event.NewEntityCreated;
import com.gra.recist.domain.repository.AggregateRoot;
import com.gra.recist.domain.repository.Entity;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class RecistAnalysis extends AggregateRoot {

    private final Set<Artefact> artefacts = new HashSet<>();


    public Set<Artefact> getArtefacts() {
        // TODO should return copy
        return artefacts;
    }

    public RecistAnalysis() {
        NewEntityCreated event = new NewEntityCreated(SerializationUtils.clone(this));
        this.sendEvent(event);
    }

    public void addArtefact() {
        Artefact artefact = new Artefact(0);
        artefacts.add(artefact);
        EntityModified event = new EntityModified(SerializationUtils.clone(this));
        this.sendEvent(event);
    }

    public void updateArtefact(UUID artefactId, Integer lesionSize) {
        artefacts.add(new Artefact(artefactId, lesionSize));
    }
}
