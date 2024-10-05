package com.gra.recist.presentation.studypresenter;

import com.google.inject.Inject;
import com.gra.recist.application.service.StudyService;
import com.gra.recist.domain.model.DicomData;
import com.gra.recist.domain.model.valueobject.DicomDataSource;
import com.gra.recist.domain.model.valueobject.FrameId;
import com.gra.recist.presentation.util.MainFxLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class StudyPresenter implements Initializable {

    @FXML
    public VBox thumbPanel;
    @Inject
    private MainFxLoader mainFxLoader;

    @Inject
    StudyService studyService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(System.identityHashCode(studyService));
    }

    public void setStudySource(Path dataPath) {
        DicomDataSource dataSource = DicomDataSource.createForFileSystem(dataPath);
        List<FrameId> frameIds = studyService.loadAllHeaders(dataSource);
        Map<String, List<FrameId>> framesBySeries = frameIds.stream().collect(Collectors.groupingBy(FrameId::seriesInstanceId));

        framesBySeries.entrySet().forEach(entry -> {
            try {
                MainFxLoader.ViewControllerReference<Parent, Thumb> thumbReference = mainFxLoader.load(thumbPanel, Thumb.class);
                thumbPanel.getChildren().add(thumbReference.viewNode());
                List<CompletableFuture<DicomData>> loadingDicomData = studyService.loadStudy(entry.getValue());
                thumbReference.controller().setDicomData(entry.getKey(), loadingDicomData);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

//
//        studyService.loadStudy(dataSource)
//                .forEach(dicomData -> dicomData.thenAccept());
    }
}
