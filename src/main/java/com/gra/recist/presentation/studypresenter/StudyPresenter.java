package com.gra.recist.presentation.studypresenter;

import com.google.inject.Inject;
import com.gra.recist.application.readmodel.StudyReadModel;
import com.gra.recist.application.service.StudyService;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class StudyPresenter implements Initializable {

    @Inject
    StudyService studyService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(System.identityHashCode(studyService));
    }

    public void setStudySource(String dataSource) {

    }
}
