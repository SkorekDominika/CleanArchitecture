package com.gra.recist.presentation.studypresenter;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.gra.recist.presentation.studypresenter.event.SeriesSelected;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;

import java.net.URL;
import java.util.ResourceBundle;

public class StudyPane implements Initializable {

    @Inject
    @Named("windowMQ")
    MBassador<Object> mBassador;
    @FXML
    public Label seriesInstanceUIdLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // MBassador by default uses WeakReferences
        mBassador.subscribe(this);
    }

    @Handler
    private void handleSeriesSelected(SeriesSelected seriesSelected) {
        Platform.runLater(() -> seriesInstanceUIdLabel.setText(seriesSelected.seriesInstanceUid()));
    }
}
