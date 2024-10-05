package com.gra.recist.presentation.studypresenter;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.gra.recist.domain.model.DicomData;
import com.gra.recist.presentation.studypresenter.event.SeriesSelected;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import net.engio.mbassy.bus.MBassador;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class Thumb implements Initializable {

    @FXML
    public Label loadingLabel;
    @FXML
    public Label loadingStatus;
    @FXML
    public Label seriesUidLabel;
    @Inject
    @Named("windowMQ")
    private MBassador<Object> mBassador;

    private Counter counter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Thumb:" + Objects.toIdentityString(mBassador));
    }

    public void setDicomData(String seriesInstanceUid, List<CompletableFuture<DicomData>> loadingDicomData) {
        seriesUidLabel.setText(seriesInstanceUid);
        counter = new Counter(loadingDicomData.size());
        counter.getCounterProperty().addListener((ocs, oldVal, newVal) -> Platform.runLater(() -> loadingStatus.setText(counter.toString())));

        loadingDicomData.forEach(dicomDataFuture -> dicomDataFuture.thenAccept(dd -> counter.increment()));

        CompletableFuture.allOf(loadingDicomData.toArray(new CompletableFuture[0]))
                .thenRun(() -> Platform.runLater(() -> loadingLabel.setVisible(false)));
    }

    @FXML
    public void onSelected(MouseEvent mouseEvent) {
        mBassador.publish(new SeriesSelected(this.seriesUidLabel.getText()));
    }

    private static class Counter {
        int total;
        SimpleIntegerProperty counter;

        public Counter(int total) {
            this.total = total;
            this.counter = new SimpleIntegerProperty(0);
        }

        public synchronized void increment() {
            int i = counter.get();
            counter.set(i + 1);
        }

        ReadOnlyIntegerProperty getCounterProperty() {
            return counter;
        }

        public String toString() {
            return counter.get() + "/" + total;
        }
    }
}
