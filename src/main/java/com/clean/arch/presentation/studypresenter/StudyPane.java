package com.clean.arch.presentation.studypresenter;

import com.clean.arch.application.service.StudyService;
import com.clean.arch.domain.model.DicomData;
import com.clean.arch.domain.model.valueobject.FrameId;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import net.engio.mbassy.bus.MBassador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class StudyPane implements Initializable {

    @FXML
    public Label seriesInstanceUIdLabel;
    @FXML
    public ImageView imageView;
    @Inject
    @Named("windowMQ")
    MBassador<Object> mBassador;

    @Inject
    StudyService studyService;
    private Set<FrameId> frameIds;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // MBassador by default uses WeakReferences
        mBassador.subscribe(this);
    }

    public void visualizeFrames(Set<FrameId> frameIds) {
        this.frameIds = frameIds;
        FrameId frameId = frameIds.stream().findFirst().orElseThrow(() -> new IllegalStateException("No frames to show!"));
        visualizeStudyData(frameId);
    }

    public void visualizeStudyData(FrameId frameId) {
        studyService.loadStudy(frameId).thenAccept(dicomData -> {
            byte[] imageRawData = dicomData.getImage();
            try (InputStream is = new ByteArrayInputStream(imageRawData)) {
                Image image = new Image(is);
                Platform.runLater(() -> imageView.setImage(image));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
