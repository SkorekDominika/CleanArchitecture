package com.gra.recist.presentation.analyser;

import com.google.inject.Inject;
import com.gra.recist.presentation.util.MainFxLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RecistAnalyser {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}