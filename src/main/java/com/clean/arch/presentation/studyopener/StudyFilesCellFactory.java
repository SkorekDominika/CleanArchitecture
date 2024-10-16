package com.clean.arch.presentation.studyopener;

import com.clean.arch.domain.model.valueobject.DicomDataSource;
import com.clean.arch.presentation.studypresenter.StudyPresenter;
import com.clean.arch.presentation.util.HangingProtocolCoordinator;
import com.clean.arch.presentation.util.MainFxLoader;
import com.clean.arch.presentation.util.MainFxLoader.ViewControllerReference;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Singleton
public class StudyFilesCellFactory
        implements Callback<ListView<PatientStudyData>, ListCell<PatientStudyData>> {

    @Inject
    private MainFxLoader mainFxLoader;

    @Inject
    private HangingProtocolCoordinator hangingProtocolCoordinator;

    @Override
    public ListCell<PatientStudyData> call(ListView<PatientStudyData> param) {
        ListCell<PatientStudyData> listCell =
                new ListCell<>() {
                    @Override
                    public void updateItem(PatientStudyData data, boolean empty) {
                        super.updateItem(data, empty);
                        if (empty || data == null) {
                            setGraphic(null);
                        } else {
                            try {
                                ViewControllerReference<Parent, StudyFile> node =
                                        mainFxLoader.load(param.getParent(), StudyFile.class);
                                StudyFile controller = node.controller();
                                controller.patientNameLabel.setText(data.patentName());
                                setGraphic(node.viewNode());
                            } catch (IOException e) {
                                System.out.println("Exception during creating patient cell: " + e);
                            }
                        }
                    }
                };

        listCell.setOnMouseClicked(
                e -> {
                    if (!listCell.isEmpty()) {
                        try {
                            openStudyInNewWindow(param, listCell);
                            e.consume();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });

        return listCell;
    }

    private void openStudyInNewWindow(
            ListView<PatientStudyData> param, ListCell<PatientStudyData> listCell) throws IOException {
        String patientName = listCell.getItem().patentName();
        DicomDataSource dataSource = DicomDataSource.createForFileSystem(listCell.getItem().studyPath());

        List<Stage> views =
                hangingProtocolCoordinator.prepareViews(
                        StudyPresenter.class, controller -> controller.setStudySource(dataSource));
        views.forEach(
                view -> {
                    view.setTitle(patientName);
                    view.initOwner(param.getParent().getScene().getWindow());
                    view.show();
                });
    }
}
