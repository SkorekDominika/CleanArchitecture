package com.gra.recist.presentation.studyopener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.gra.recist.application.service.StudyService;
import com.gra.recist.presentation.studypresenter.StudyPresenter;
import com.gra.recist.presentation.util.MainFxLoader;
import com.gra.recist.presentation.util.MainFxLoader.ViewControllerReference;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

@Singleton
public class StudyFilesCellFactory implements Callback<ListView<String>, ListCell<String>> {

    @Inject
    private MainFxLoader mainFxLoader;

    @Inject
    private StudyService studyService;

    @Override
    public ListCell<String> call(ListView<String> param) {
        ListCell<String> listCell = new ListCell<>() {
            @Override
            public void updateItem(String data, boolean empty) {
                super.updateItem(data, empty);
                if (empty || data == null) {
                    setGraphic(null);
                } else {
                    try {
                        ViewControllerReference<Parent, StudyFile> node = mainFxLoader.load(StudyFile.class);
                        StudyFile controller = node.controller();
                        controller.patientNameLabel.setText(data);
                        setGraphic(node.viewNode());
                    } catch (IOException e) {
                        System.out.println("Exception during creating patient cell: " + e);
                    }
                }
            }
        };

        listCell.setOnMouseClicked(e -> {
            if (!listCell.isEmpty()) {
                try {
                    ViewControllerReference<Parent, StudyPresenter> presenter = mainFxLoader.load(StudyPresenter.class);
                    Stage stage = new Stage();
                    stage.setTitle("My New Stage Title");
                    stage.setScene(new Scene(presenter.viewNode(), 600, 450));
                    stage.initOwner(param.getParent().getScene().getWindow());
                    stage.show();
                    System.out.println("You clicked on " + listCell.getItem());
                    e.consume();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        return listCell;
    }


}