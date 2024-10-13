package com.clean.arch.presentation.studypresenter;

import com.clean.arch.application.service.StudyService;
import com.clean.arch.domain.model.valueobject.FrameId;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import net.engio.mbassy.bus.MBassador;

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
    private List<FrameId> frameIds;
    private FrameId currentFrame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // MBassador by default uses WeakReferences
        mBassador.subscribe(this);
    }

    public void visualizeFrames(Set<FrameId> frameIds) {
        this.frameIds = frameIds.stream().sorted(Comparator.comparing(FrameId::sopInstanceId)).collect(Collectors.toList());
        currentFrame = frameIds.stream().findFirst().orElseThrow(() -> new IllegalStateException("No frames to show!"));
        visualizeStudyData(currentFrame);
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

    public void onScroll(ScrollEvent scrollEvent) {
        int currentIdx = frameIds.indexOf(currentFrame);
        int nextIdx = currentIdx;
        if (scrollEvent.getDeltaY() > 1) {
            if (currentIdx < frameIds.size() - 1) {
                nextIdx = currentIdx + 1;
            }
        } else {
            if (currentIdx > 0) {
                nextIdx = currentIdx - 1;
            }
        }
        FrameId newFrameId = frameIds.get(nextIdx);
        currentFrame = newFrameId;
        visualizeStudyData(currentFrame);
    }
}
