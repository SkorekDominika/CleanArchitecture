package com.clean.arch.presentation.studypresenter;

import com.clean.arch.presentation.studypresenter.command.SelectSeries;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;

import java.net.URL;
import java.util.ResourceBundle;

public class StudyPane implements Initializable {

    @FXML
    public Label seriesInstanceUIdLabel;
    @Inject
    @Named("windowMQ")
    MBassador<Object> mBassador;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // MBassador by default uses WeakReferences
        mBassador.subscribe(this);
    }

    @Handler
    private void handleSelectSeriesCommand(SelectSeries selectSeries) {
        Platform.runLater(() -> seriesInstanceUIdLabel.setText(selectSeries.seriesInstanceUid()));
    }
}
