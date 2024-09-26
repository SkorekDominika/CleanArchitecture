package com.gra.recist.presentation.studyopener;

import com.google.inject.Inject;
import com.gra.recist.presentation.util.MainFxLoader;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Arrays;
import java.util.List;

public class StudyOpener {

    List<String> PATIENTS_NAMES = Arrays.asList("Jan Kowalski", "Adam Przestroga", "Danuta Kwiatkowska");

    @FXML
    ListView<String> studiesFilesList;

    @Inject
    private MainFxLoader mainFxLoader;
    @Inject
    private StudyFilesCellFactory studyFilesCellFactory;


    @FXML
    public void initialize() {
        studiesFilesList.setCellFactory(studyFilesCellFactory);
        studiesFilesList.getItems().addAll(PATIENTS_NAMES);
    }
}