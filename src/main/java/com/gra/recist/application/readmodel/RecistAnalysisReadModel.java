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
    public void dispatchEvent(IEvent<RecistAnalysis> event) {
        switch (event) {
            case EntityModified<RecistAnalysis> entityModified -> update(entityModified);
            default -> throw new UnsupportedOperationException("Unhandled event: %s exception".formatted(event));
        }
    }

    private void update(IEvent<RecistAnalysis> event) {
        RecistAnalysis recistAnalysis = event.getEntity();
        name.setValue(recistAnalysis.getName());
    }
}
