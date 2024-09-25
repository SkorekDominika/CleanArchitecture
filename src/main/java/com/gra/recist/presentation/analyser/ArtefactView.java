package com.gra.recist.presentation.analyser;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.util.UUID;

public class ArtefactView {

    public UUID id;
    @FXML
    public Label artefactLabel;
    @FXML
    public Spinner<Integer> lesionSizeSpinner;

    @FXML
    public void initialize() {
        lesionSizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
    }

}
