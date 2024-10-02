package com.gra.recist.presentation.studypresenter;

import com.google.inject.Inject;
import com.gra.recist.application.service.StudyService;
import com.gra.recist.domain.model.DicomData;
import com.gra.recist.domain.model.valueobject.DicomDataSource;
import com.gra.recist.domain.model.valueobject.FrameId;
import javafx.fxml.Initializable;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class StudyPresenter implements Initializable {

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


        studyService.loadStudy(dataSource)
                .forEach(dicomData -> dicomData.thenAccept());
    }
}
