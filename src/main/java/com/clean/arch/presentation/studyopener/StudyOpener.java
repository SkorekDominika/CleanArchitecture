package com.clean.arch.presentation.studyopener;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class StudyOpener {

    List<PatientStudyData> PATIENTS;
    @FXML
    ListView<PatientStudyData> studiesFilesList;
    @Inject
    private StudyFilesCellFactory studyFilesCellFactory;

    {
        try {
            PATIENTS =
                    Arrays.asList(
                            new PatientStudyData(
                                    "Jan Kowalski", Paths.get(getClass().getResource("/study1").toURI())),
                            new PatientStudyData(
                                    "Adam Przestroga", Paths.get(getClass().getResource("/study1").toURI())),
                            new PatientStudyData(
                                    "Danuta Kwiatkowska", Paths.get(getClass().getResource("/study1").toURI())));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        studiesFilesList.setCellFactory(studyFilesCellFactory);
        studiesFilesList.getItems().addAll(PATIENTS);
    }
}
