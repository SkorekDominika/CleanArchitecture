package com.gra.recist.application.readmodel;

import com.gra.recist.domain.event.EntityModified;
import com.gra.recist.domain.event.IEvent;
import com.gra.recist.domain.model.RecistAnalysis;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;


public class RecistAnalysisReadModel implements ReadModel<RecistAnalysis> {

    Property<String> name;

    public RecistAnalysisReadModel(String name) {
        this.name.setValue(name);
    }

    public ReadOnlyProperty<String> getName() {
        return name;
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
            name.setValue(recistAnalysis.getName());
        } else {
            System.out.println("Entity is not a RecistAnalysis!");
        }
    }
}
