package com.clean.arch.presentation.studypresenter;

import com.clean.arch.domain.model.DicomData;
import com.clean.arch.domain.model.valueobject.FrameId;
import com.clean.arch.presentation.studypresenter.command.DuplicateSeries;
import com.clean.arch.presentation.studypresenter.command.VisualizeSeries;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.engio.mbassy.bus.MBassador;

import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Thumb implements Initializable {

    @FXML
    public Label loadingLabel;
    @FXML
    public Label loadingStatus;
    @FXML
    public Label seriesUidLabel;
    @FXML
    public AnchorPane thumbPane;
    @FXML
    public Button moreButton;

    @Inject
    @Named("windowMQ")
    private MBassador<Object> mBassador;

    private Counter counter;
    private ContextMenu contextMenu;
    private Set<FrameId> frameIds;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contextMenu = createContextMenu();
        System.out.println("Thumb:" + Objects.toIdentityString(mBassador));
    }

    public void setDicomData(String seriesInstanceUid, Map<FrameId, CompletableFuture<DicomData>> loadingDicomDataByFrameId) {
        frameIds = loadingDicomDataByFrameId.keySet();
        Collection<CompletableFuture<DicomData>> loadingDicomData = loadingDicomDataByFrameId.values();

        seriesUidLabel.setText(seriesInstanceUid);
        counter = new Counter(loadingDicomData.size());
        counter
                .getCounterProperty()
                .addListener(change -> Platform.runLater(() -> loadingStatus.setText(counter.toString())));

        loadingDicomData.forEach(
                dicomDataFuture -> dicomDataFuture.thenAccept(dd -> counter.increment()));

        CompletableFuture.allOf(loadingDicomData.toArray(new CompletableFuture[0]))
                .thenRun(() -> Platform.runLater(() -> loadingLabel.setVisible(false)));
    }

    @FXML
    public void onSelected(MouseEvent mouseEvent) {
        mBassador.publish(new VisualizeSeries(frameIds));
    }

    @FXML
    public void onMoreButtonSelected(MouseEvent mouseEvent) {
        contextMenu.show(moreButton, Side.BOTTOM, 0, 0);
        mouseEvent.consume();
    }

    private ContextMenu createContextMenu() {
        MenuItem menuItem = new MenuItem("Duplicate");
        menuItem.setOnAction(e -> mBassador.publish(new DuplicateSeries(frameIds)));
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(menuItem);
        return contextMenu;
    }

    private static class Counter {
        private final int total;
        private final SimpleIntegerProperty counter;

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