package com.gra.recist.presentation.studyopener;

import com.google.inject.Inject;
import com.gra.recist.presentation.util.MainFxLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.IOException;

public class StudyFilesCellFactory implements Callback<ListView<String>, ListCell<String>> {

    @Inject
    private MainFxLoader mainFxLoader;

    @Override
    public ListCell<String> call(ListView<String> param) {
        return new ListCell<>() {
            @Override
            public void updateItem(String data, boolean empty) {
                super.updateItem(data, empty);
                if (empty || data == null) {
                    setGraphic(null);
                } else {
                    try {
                        MainFxLoader.ViewControllerReference<Parent, StudyFile> node = mainFxLoader.load(StudyFile.class);
                        StudyFile controller = node.controller();
                        controller.patientNameLabel.setText(data);
                        setGraphic(node.viewNode());
                    } catch (IOException e) {
                        System.out.println("Exception during creating patient cell: " + e);
                    }
                }
            }
        };
    }
}