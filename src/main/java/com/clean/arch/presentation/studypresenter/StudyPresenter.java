package com.clean.arch.presentation.studypresenter;

import com.clean.arch.application.common.IApplicationEvent;
import com.clean.arch.application.common.event.SeriesDuplicatedEvent;
import com.clean.arch.application.service.StudyService;
import com.clean.arch.domain.model.DicomData;
import com.clean.arch.domain.model.valueobject.DicomDataSource;
import com.clean.arch.domain.model.valueobject.FrameId;
import com.clean.arch.presentation.studypresenter.command.DuplicateSeries;
import com.clean.arch.presentation.util.MainFxLoader;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StudyPresenter implements Initializable {

    @FXML
    public VBox thumbPanel;
    @Inject
    StudyService studyService;
    @Inject
    private MainFxLoader mainFxLoader;
    @Inject
    @Named("windowMQ")
    private MBassador<Object> mBassador;
    private Consumer<IApplicationEvent> studyServiceEventConsumer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mBassador.subscribe(this);
        studyServiceEventConsumer = event -> {
            switch (event) {
                case SeriesDuplicatedEvent e -> addThumb(e.seriesInstanceId(), e.duplicatedFrames());
                default -> {
                }
            }
        };
        studyService.subscribe(studyServiceEventConsumer);
        System.out.println(System.identityHashCode(studyService));
    }

    public void setStudySource(Path dataPath) {
        DicomDataSource dataSource = DicomDataSource.createForFileSystem(dataPath);
        List<FrameId> frameIds = studyService.loadAllHeaders(dataSource);
        Map<String, List<FrameId>> framesBySeries = frameIds.stream().collect(Collectors.groupingBy(FrameId::seriesInstanceId));

        framesBySeries.forEach(this::addThumb);
    }

    private void addThumb(String key, List<FrameId> value) {
        Platform.runLater(() -> {
            try {
                MainFxLoader.ViewControllerReference<Parent, Thumb> thumbReference = mainFxLoader.load(thumbPanel, Thumb.class);
                thumbPanel.getChildren().add(thumbReference.viewNode());
                Map<FrameId, CompletableFuture<DicomData>> loadingDicomData = studyService.loadStudy(value);
                thumbReference.controller().setDicomData(key, loadingDicomData);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    @Handler
    private void handleDuplicateSeries(DuplicateSeries duplicateSeries) {
        try {
            studyService.duplicateSeries(duplicateSeries.frameIds());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
